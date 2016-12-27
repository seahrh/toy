package toy.rm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

public final class ToteOptimizer {
	private static final Logger log = LoggerFactory.getLogger(ToteOptimizer.class);
	private static final String INPUT_FILE_PATH = System.getProperty("toy.products");
	private static final String INPUT_FILE_SEPARATOR = ",";
	/**
	 * Tote volume capacity in cm^3
	 */
	private static final int TOTE_CAPACITY = 45 * 30 * 35;

	private ToteOptimizer() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		int sumOfIds = 0;
		int volume = 0;
		int weight = 0;
		TreeMultimap<Double, Product> products = rankedProducts();
		Double rank;
		Product p;
		int pvol;
		for (Map.Entry<Double, Product> entry : products.entries()) {
			rank = entry.getKey();
			p = entry.getValue();
			log.debug("rank [{}] product:{}", rank, p);
			pvol = p.volume();
			if (volume + pvol > TOTE_CAPACITY) {
				continue;
			}
			volume += pvol;
			weight += p.weight();
			sumOfIds += p.id();
		}
		log.info("sumOfIds [{}] volume [{}/{}] weight [{}]", sumOfIds, volume, TOTE_CAPACITY, weight);
	}

	private static TreeMultimap<Double, Product> rankedProducts()
			throws IOException {
		final int nTokens = 6;
		int size;
		String line;
		List<String> tokens;
		TreeMultimap<Double, Product> ret = TreeMultimap.create(
				Ordering.natural()
					.reverse(), Product.ORDER_BY_VOLUME);
		Product p;
		File file = new File(INPUT_FILE_PATH);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				tokens = Splitter.on(INPUT_FILE_SEPARATOR)
					.trimResults()
					.splitToList(line);
				size = tokens.size();
				if (size != nTokens) {
					log.error("Expecting {} tokens but found {}", nTokens, size);
					throw new IllegalStateException();
				}
				p = new Product.Builder().id(Integer.parseInt(tokens.get(0)))
					.price(Integer.parseInt(tokens.get(1)))
					.length(Integer.parseInt(tokens.get(2)))
					.width(Integer.parseInt(tokens.get(3)))
					.height(Integer.parseInt(tokens.get(4)))
					.weight(Integer.parseInt(tokens.get(5)))
					.build();
				ret.put(p.priceToVolumeWeightRatio(), p);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return ret;
	}

}
