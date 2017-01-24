package toy.bx;

import static toy.bx.ItemCf.pairKey;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import toy.util.FileUtil;
import toy.util.MathUtil;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Sets;

public final class ItemCfTrainer {
	private static final Logger log = LoggerFactory.getLogger(ItemCfTrainer.class);
	private static final String RATINGS_INPUT_FILE_PATH = System.getProperty("toy.ratings");
	private static final String TEST_SET_FILE_PATH = System.getProperty("toy.test");
	private static final String SIMILARITY_MATRIX_FILE_PATH = System.getProperty("toy.sim");
	private static final String RATINGS_TABLE_FILE_PATH = System.getProperty("toy.ratings-train");
	private static final float TRAIN_PROPORTION = 0.8F;
	private static final float TEST_PROPORTION = 1 - TRAIN_PROPORTION;
	private static List<List<String>> trainSet = new ArrayList<>(
			(int) (TRAIN_PROPORTION * 1000000));
	private static List<List<String>> testSet = new ArrayList<>(
			(int) (TEST_PROPORTION * 1000000));
	private static Map<String, Float> simMatrix = new HashMap<>(64_000_000);
	private static ImmutableTable<String, String, Integer> ratingTable;

	private ItemCfTrainer() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Main: started...");
		extract();
		ratingTable();
		train();
		export();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Main: completed ({}s)", elapsedTime / 1000);
	}
	
	private static void ratingTable() {
		long startTime = System.currentTimeMillis();
		log.info("ratingTable: started...");
		ImmutableTable.Builder<String, String, Integer> ratingTableBuilder = ImmutableTable.builder();
		String isbn;
		String uid;
		Integer rating;
		for (List<String> tokens : trainSet) {
			uid = tokens.get(0)
				.toLowerCase();
			isbn = tokens.get(1)
				.toLowerCase();
			rating = Integer.parseInt(tokens.get(2));
			ratingTableBuilder.put(isbn, uid, rating);
		}
		ratingTable = ratingTableBuilder.build();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("ratingTable: completed ({}s)", elapsedTime / 1000);
	}

	private static void train() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Train: started...");
		ImmutableSet<String> itemsSet = ratingTable.rowKeySet();
		final String[] items = itemsSet.toArray(new String[itemsSet.size()]);
		ImmutableMap<String, Map<String, Integer>> itemMap = ratingTable.rowMap();
		Map<String, Integer> uidToRating;
		Map<String, Integer> otherUidToRating;
		Set<String> raters;
		Set<String> otherRaters;
		Set<String> commonRaters;
		Float sim;
		String isbn;
		String otherIsbn;
		String pair;
		int progress = 0;
		final int progressInterval = 1_000_000;
		for (int i = 0; i < items.length; i++) {
			for (int j = i + 1; j < items.length; j++) {
				isbn = items[i];
				uidToRating = itemMap.get(isbn);
				// this item has at least 1 rating
				raters = uidToRating.keySet();
				otherIsbn = items[j];
				otherUidToRating = itemMap.get(otherIsbn);
				// other item has at least 1 rating
				otherRaters = otherUidToRating.keySet();
				// Intersect raters of this item vs. other item
				commonRaters = Sets.intersection(raters, otherRaters);
				if (commonRaters.isEmpty()) {
					// No raters in common; skip
					continue;
				}
				pair = pairKey(isbn, otherIsbn);
				sim = (float) cosineSimilarity(commonRaters, uidToRating,
						otherUidToRating);
				log.debug("sim={} isbn={} otherIsbn={}", sim, isbn, otherIsbn);
				if (++progress % progressInterval == 0) {
					log.info("{}M sim computed", progress / progressInterval);
				}
				simMatrix.put(pair, sim);
			}
		}
		log.info("{} sim computed", progress);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Train: completed ({}s)", elapsedTime / 1000);
	}

	private static double cosineSimilarity(Set<String> raters,
			Map<String, Integer> uidToRating,
			Map<String, Integer> otherUidToRating) {
		int size = raters.size();
		double[] ratings = new double[size];
		double[] otherRatings = new double[size];
		int i = 0;
		for (String rater : raters) {
			ratings[i] = (double) uidToRating.get(rater);
			otherRatings[i] = (double) otherUidToRating.get(rater);
			i++;
		}
		log.debug("ratings={}\notherRatings={}", ratings, otherRatings);
		return MathUtil.cosineSimilarity(ratings, otherRatings);
	}

	private static void extract() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Extract: started...");
		final int nHeaderRows = 1;
		final CharMatcher separator = CharMatcher.anyOf("\";\\");
		final boolean omitEmptyStrings = true;
		List<List<String>> in = FileUtil.read(RATINGS_INPUT_FILE_PATH,
				separator, nHeaderRows, omitEmptyStrings);
		int size = in.size();
		log.info("ratings size={}, before removing implicit ratings", size);
		in = removeImplicitRatings(in);
		size = in.size();
		log.info("ratings size={}, after removing implicit ratings", size);
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

	private static List<List<String>> removeImplicitRatings(
			List<List<String>> ratings) {
		List<List<String>> ret = new ArrayList<>(ratings.size());
		int rating;
		for (List<String> tokens : ratings) {
			rating = Integer.parseInt(tokens.get(2));
			if (rating != 0) {
				ret.add(tokens);
			}
		}
		return ret;
	}

	private static void exportTestSet() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("exportTestSet: started...");
		final String separator = "\t";
		FileUtil.write(testSet, TEST_SET_FILE_PATH, separator);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("exportTestSet: completed ({}s)", elapsedTime / 1000);
	}

	private static void exportRatingsTable() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("exportRatingsTable: started...");
		List<List<String>> data = new ArrayList<>(ratingTable.size());
		List<String> tokens;
		String isbn;
		String uid;
		String rating;
		Map<String, Integer> userMap;
		ImmutableMap<String, Map<String, Integer>> itemMap = ratingTable.rowMap();
		for (Map.Entry<String, Map<String, Integer>> item : itemMap.entrySet()) {
			tokens = new ArrayList<>();
			isbn = item.getKey();
			userMap = item.getValue();
			for (Map.Entry<String, Integer> user : userMap.entrySet()) {
				uid = user.getKey();
				rating = String.valueOf(user.getValue());
				tokens.add(uid);
				tokens.add(isbn);
				tokens.add(rating);
				data.add(tokens);
			}
		}
		final String separator = "\t";
		FileUtil.write(data, RATINGS_TABLE_FILE_PATH, separator);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("exportRatingsTable: completed ({}s)", elapsedTime / 1000);
	}

	private static void exportSimilarityMatrix() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("exportSimilarityMatrix: started...");
		List<List<String>> data = new ArrayList<>(simMatrix.size());
		List<String> tokens;
		Float sim;
		// Round similarity to 8 decimal places
		DecimalFormat df = new DecimalFormat("#.########");
		df.setRoundingMode(RoundingMode.UP);
		for (Map.Entry<String, Float> entry : simMatrix.entrySet()) {
			sim = entry.getValue();
			tokens = new ArrayList<>();
			tokens.add(entry.getKey());
			tokens.add(df.format((double) sim));
			data.add(tokens);
		}
		final String separator = "\t";
		FileUtil.write(data, SIMILARITY_MATRIX_FILE_PATH, separator);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("exportSimilarityMatrix: completed ({}s)", elapsedTime / 1000);
	}
	
	private static void export() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("export: started...");
		exportTestSet();
		exportRatingsTable();
		exportSimilarityMatrix();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("export: completed ({}s)", elapsedTime / 1000);
	}

}
