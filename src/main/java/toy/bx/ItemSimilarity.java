package toy.bx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;

import toy.util.FileUtil;

public final class ItemSimilarity {
	private static final Logger log = LoggerFactory.getLogger(ItemSimilarity.class);
	private static final String RATINGS_FILE_PATH = System.getProperty("toy.ratings");
	private static final String USERS_FILE_PATH = System.getProperty("toy.users");
	private static final String BOOKS_FILE_PATH = System.getProperty("toy.books");
	private static final float TRAIN_PROPORTION = 0.8F;
	private static final float TEST_PROPORTION = 1 - TRAIN_PROPORTION;
	private static List<List<String>> trainSet = new ArrayList<>((int) (TRAIN_PROPORTION * 2000000));
	private static List<List<String>> testSet = new ArrayList<>((int) (TEST_PROPORTION * 2000000));
	private static ImmutableTable<String, String, Float> simMatrix;

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Main: started...");
		extract();
		train();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Main: completed ({}s)", elapsedTime / 1000);
	}
	
	private static void train() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Train: started...");
		ImmutableTable.Builder<String, String, Integer> ratingTableBuilder = ImmutableTable.builder();
		String isbn;
		String uid;
		Integer rating;
		for (List<String> tokens : trainSet) {
			uid = tokens.get(0).toLowerCase();
			isbn = tokens.get(1).toLowerCase();
			rating = Integer.parseInt(tokens.get(2));
			ratingTableBuilder.put(isbn, uid, rating);
		}
		ImmutableTable<String, String, Integer> ratingTable = ratingTableBuilder.build();
		ImmutableMap<String, Map<String, Integer>> itemMap = ratingTable.rowMap();
		ImmutableTable.Builder<String, String, Float> simMatrixBuilder = ImmutableTable.builder();
		Map<String, Integer> uidToRating;
		for (Map.Entry<String, Map<String, Integer>> entry : itemMap.entrySet()) {
			isbn = entry.getKey();
			uidToRating = entry.getValue();
			
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Train: completed ({}s)", elapsedTime / 1000);
	}
	
	private static void extract() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Extract: started...");
		final int nHeaderRows = 1;
		final CharMatcher separator = CharMatcher.anyOf("\";\\");
		final boolean omitEmptyStrings = true;
		List<List<String>> in = FileUtil.read(RATINGS_FILE_PATH, separator, nHeaderRows, omitEmptyStrings);
		int size = in.size(); 
		log.info("ratings size={}", size);
		log.debug("{}", in);
		Collections.shuffle(in);
		int k = (int) (TRAIN_PROPORTION * size);
		for (int i = 0; i < k; i++) {
			trainSet.add(in.get(i));
		}
		for (int i = k; i < size; i++) {
			testSet.add(in.get(i));
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Extract: completed ({}s)", elapsedTime / 1000);
	}
	
}
