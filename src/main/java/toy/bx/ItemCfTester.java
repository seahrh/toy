package toy.bx;

import static toy.bx.ItemCf.predict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import toy.util.FileUtil;
import toy.util.MathUtil;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableTable;
import com.google.common.primitives.Doubles;

public final class ItemCfTester {
	private static final Logger log = LoggerFactory.getLogger(ItemCfTester.class);
	private static final String TEST_SET_FILE_PATH = System.getProperty("toy.test");
	private static final String SIMILARITY_MATRIX_FILE_PATH = System.getProperty("toy.sim");
	private static final String RATINGS_FILE_PATH = System.getProperty("toy.ratings-train");
	private static List<List<String>> testSet = new ArrayList<>((int) (1000000));
	private static Map<String, Float> simMatrix = new HashMap<>(64_000_000);
	private static ImmutableTable<String, String, Integer> ratingTable;

	private ItemCfTester() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("main: task started...");
		init();
		eval();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("main: task completed ({}s)", elapsedTime / 1000);
	}

	private static void eval() {
		long startTime = System.currentTimeMillis();
		log.info("init: task started...");
		List<Double> predictions = new ArrayList<>(testSet.size());
		List<Double> actuals = new ArrayList<>(testSet.size());
		String isbn;
		String uid;
		Double a;
		Optional<Double> op;
		int skipped = 0;
		for (List<String> tokens : testSet) {
			uid = tokens.get(0)
				.toLowerCase();
			isbn = tokens.get(1)
				.toLowerCase();
			a = Double.parseDouble(tokens.get(2));
			op = predict(uid, isbn, ratingTable, simMatrix);
			if (!op.isPresent()) {
				skipped++;
				continue;
			}
			actuals.add(a);
			predictions.add(op.get());
		}
		double[] predictionsArr = Doubles.toArray(predictions);
		double[] actualsArr = Doubles.toArray(actuals);
		double meanAbsoluteError = MathUtil.meanAbsoluteError(predictionsArr,
				actualsArr);
		double rootMeanSquaredError = MathUtil.rootMeanSquaredError(
				predictionsArr, actualsArr);
		log.info(
				"Results:\nmeanAbsoluteError={}\nrootMeanSquaredError={}\n#predictions={}\n#skipped={}",
				meanAbsoluteError, rootMeanSquaredError, predictionsArr.length,
				skipped);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("init: task completed ({}s)", elapsedTime / 1000);
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
		List<List<String>> in = FileUtil.read(SIMILARITY_MATRIX_FILE_PATH,
				separator);
		String pair;
		Float sim;
		for (List<String> tokens : in) {
			pair = tokens.get(0);
			sim = Float.parseFloat(tokens.get(1));
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
			uid = tokens.get(0).toLowerCase();
			isbn = tokens.get(1).toLowerCase();
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
