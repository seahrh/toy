package toy.pm;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PmApi {
	private static final Logger log = LoggerFactory.getLogger(PmApi.class);
	private static final String BASE_URL = "https://fvjkpkflnc.execute-api.us-east-1.amazonaws.com";
	private static final String TRADERS_URL = BASE_URL + "/prod/traders";
	private static final String TRANSACTIONS_URL = BASE_URL + "/prod/transactions";
	private static final String API_KEY = "x-api-key";
	private static final String API_KEY_VALUE = "gaqcRZE4bd58gSAJH3XsLYBo1EvwIQo88IfYL1L5";

	private PmApi() {
		// Not meant to be instantiated
	}
	
	public static List<Trader> traders() throws IOException {
		String json = Jsoup.connect(TRADERS_URL)
			.header(API_KEY, API_KEY_VALUE)
			.ignoreContentType(true)
			.execute()
			.body();
		log.debug(json);
		return Trader.listFromJson(json);
	}

}
