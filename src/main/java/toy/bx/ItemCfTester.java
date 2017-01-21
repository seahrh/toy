package toy.bx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import toy.util.FileUtil;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;

public final class ItemCfTester {
	private static final Logger log = LoggerFactory.getLogger(ItemCfTester.class);
	private static final String TEST_SET_FILE_PATH = System.getProperty("toy.test");
	private static final String SIMILARITY_MATRIX_FILE_PATH = System.getProperty("toy.sim");
	private static final String RATINGS_FILE_PATH = System.getProperty("toy.ratings");
	private static List<List<String>> testSet = new ArrayList<>((int) (1000000));
	private static Map<String, Double> simMatrix = new HashMap<>();
	private static ImmutableTable<String, String, Integer> ratingTable;

	private ItemCfTester() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("main: task started...");
		init();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("main: task completed ({}s)", elapsedTime / 1000);
	}

	private static void init() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("init: task started...");
		importTestSet();
		importRatingTable();
		importSimilarityMatrix();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("init: task completed ({}s)", elapsedTime / 1000);
	}

	private static void importSimilarityMatrix() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("importSimilarityMatrix: started...");
		final CharMatcher separator = CharMatcher.anyOf("\t");
		List<List<String>> in = FileUtil.read(SIMILARITY_MATRIX_FILE_PATH, separator);
		String pair;
		Double sim;
		for (List<String> tokens : in) {
			pair = tokens.get(0);
			sim = Double.parseDouble(tokens.get(1));
			simMatrix.put(pair, sim);
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("importSimilarityMatrix: completed ({}s)", elapsedTime / 1000);
	}

	private static void importRatingTable() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("importRatingTable: started...");
		final CharMatcher separator = CharMatcher.anyOf("\t");
		ImmutableTable.Builder<String, String, Integer> ratingTableBuilder = ImmutableTable.builder();
		List<List<String>> in = FileUtil.read(RATINGS_FILE_PATH, separator);
		String uid;
		String isbn;
		Integer rating;
		for (List<String> tokens : in) {
			uid = tokens.get(0);
			isbn = tokens.get(1);
			rating = Integer.parseInt(tokens.get(2));
			ratingTableBuilder.put(isbn, uid, rating);
		}
		ratingTable = ratingTableBuilder.build();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("importRatingTable: completed ({}s)", elapsedTime / 1000);
	}

	private static void importTestSet() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("importTestSet: started...");
		final CharMatcher separator = CharMatcher.anyOf("\t");
		List<List<String>> in = FileUtil.read(TEST_SET_FILE_PATH, separator);
		for (List<String> tokens : in) {
			testSet.add(tokens);
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("importTestSet: completed ({}s)", elapsedTime / 1000);
	}

}
