package toy.pm;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class QuestionGetter {
	private static final Logger log = LoggerFactory.getLogger(QuestionGetter.class);
	private static final String URL = "https://fvjkpkflnc.execute-api.us-east-1.amazonaws.com/prod/question";
	private static final String API_KEY = "x-api-key";
	private static final String API_KEY_VALUE = "gaqcRZE4bd58gSAJH3XsLYBo1EvwIQo88IfYL1L5";

	private QuestionGetter() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		String json = Jsoup.connect(URL)
			.header(API_KEY, API_KEY_VALUE)
			.ignoreContentType(true)
			.execute()
			.body();
		log.info(json);
	}

}
