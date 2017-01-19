package toy.bx;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;

import toy.util.FileUtil;

public final class ItemSimilarity {
	private static final Logger log = LoggerFactory.getLogger(ItemSimilarity.class);
	private static final String RATINGS_FILE_PATH = System.getProperty("toy.ratings");
	private static final String USERS_FILE_PATH = System.getProperty("toy.users");
	private static final String BOOKS_FILE_PATH = System.getProperty("toy.books");
	private static List<List<String>> trainSet;
	private static List<List<String>> testSet;

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Main started...");
		extract();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Main completed ({}s)", elapsedTime / 1000);
	}
	
	private static void extract() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Extract started...");
		final int nHeaderRows = 1;
		final CharMatcher separator = CharMatcher.anyOf("\";");
		final boolean omitEmptyStrings = true;
		List<List<String>> in = FileUtil.read(RATINGS_FILE_PATH, separator, nHeaderRows, omitEmptyStrings);
		log.info("ratings size={}", in.size());
		log.debug("{}", in);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Extract completed ({}s)", elapsedTime / 1000);
	}
	
}
