package toy.bx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Sets;

import toy.util.FileUtil;
import toy.util.MathUtil;

public final class ItemSimilarity {
	private static final Logger log = LoggerFactory.getLogger(ItemSimilarity.class);
	private static final String RATINGS_FILE_PATH = System.getProperty("toy.ratings");
	private static final String USERS_FILE_PATH = System.getProperty("toy.users");
	private static final String BOOKS_FILE_PATH = System.getProperty("toy.books");
	private static final float TRAIN_PROPORTION = 0.8F;
	private static final float TEST_PROPORTION = 1 - TRAIN_PROPORTION;
	private static List<List<String>> trainSet = new ArrayList<>(
			(int) (TRAIN_PROPORTION * 1000000));
	private static List<List<String>> testSet = new ArrayList<>(
			(int) (TEST_PROPORTION * 1000000));
	private static ImmutableTable<String, String, Double> simMatrix;
	private static ImmutableTable<String, String, Integer> ratingTable;

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Main: started...");
		extract();
		train();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("Main: completed ({}s)", elapsedTime / 1000);
	}
	
	private static void test() {
		
	}

	private static Optional<Double> predict(String uid, String isbn) {
		ImmutableMap<String, Integer> ratings = ratingTable.column(uid);
		if (ratings.isEmpty()) {
			// User has not rated any items, so cannot make prediction.
			return Optional.absent();
		}
		Double ret;
		Integer rating = ratings.get(isbn);
		if (rating != null) {
			log.warn(
					"user has already rated this item, so no prediction was made.\nuid={} isbn={}",
					uid, isbn);
			return Optional.absent();
		}
		String ratedIsbn;
		double nu = 0;
		double de = 0;
		Double sim;
		for (Map.Entry<String, Integer> entry : ratings.entrySet()) {
			ratedIsbn = entry.getKey();
			rating = entry.getValue();
			sim = simMatrix.get(isbn, ratedIsbn);
			if (sim == null) {
				sim = simMatrix.get(ratedIsbn, isbn);
				if (sim == null) {
					log.error("Similarity score is missing for isbn={}, ratedIsbn={}", isbn, ratedIsbn);
					throw new IllegalStateException();
				}
			}
			nu += sim * rating;
			de += sim;
		}
		ret = nu / de;
		return Optional.of(ret);
	}

	private static void train() throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("Train: started...");
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
		ImmutableMap<String, Map<String, Integer>> itemMap = ratingTable.rowMap();
		ImmutableTable.Builder<String, String, Double> simMatrixBuilder = ImmutableTable.builder();
		Map<String, Integer> uidToRating;
		Map<String, Integer> otherUidToRating;
		Set<String> raters;
		Set<String> otherRaters;
		Set<String> commonRaters;
		Double sim;
		String otherIsbn;
		for (Map.Entry<String, Map<String, Integer>> entry : itemMap.entrySet()) {
			isbn = entry.getKey();
			uidToRating = entry.getValue();
			raters = uidToRating.keySet();
			for (Map.Entry<String, Map<String, Integer>> other : itemMap.entrySet()) {
				otherIsbn = other.getKey();
				if (isbn.equals(otherIsbn)) {
					continue;
				}
				otherUidToRating = other.getValue();
				otherRaters = otherUidToRating.keySet();
				// Intersect raters of this item vs. other item
				commonRaters = Sets.intersection(raters, otherRaters);
				if (commonRaters.isEmpty()) {
					// No raters in common; skip
					continue;
				}
				sim = cosineSimilarity(commonRaters, uidToRating,
						otherUidToRating);
				log.debug("sim={} isbn={} otherIsbn={}", sim.toString(), isbn,
						otherIsbn);
				simMatrixBuilder.put(isbn, otherIsbn, sim);
			}
		}
		simMatrix = simMatrixBuilder.build();
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
		List<List<String>> in = FileUtil.read(RATINGS_FILE_PATH, separator,
				nHeaderRows, omitEmptyStrings);
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

}
