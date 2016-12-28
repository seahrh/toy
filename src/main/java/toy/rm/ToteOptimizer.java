package toy.rm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
		int sumVolume = 0;

		Set<Product> temp = new HashSet<>();
		Products products = products();
		TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume = products.orderByPriceToVolumeRatioDescendingAndVolume();
		TreeMultimap<Integer, Product> orderByVolumeAndWeight = products.orderByVolumeAndWeight();
		Product p;
		Double priceToVolumeRatio;
		int volume;
		int sumPrice = 0;
		for (Map.Entry<Double, Product> entry : orderByPriceToVolumeRatioDescendingAndVolume.entries()) {
			p = entry.getValue();
			priceToVolumeRatio = entry.getKey();
			log.debug("priceToVolumeRatio [{}] {}", priceToVolumeRatio, p);
			volume = p.volume();
			if (sumVolume + volume > TOTE_CAPACITY) {
				continue;
			}
			sumVolume += volume;
			sumPrice += p.price();
			temp.add(p);
			if (sumVolume == TOTE_CAPACITY) {
				break;
			}
		}
		int sumIds = 0;
		sumVolume = 0;
		int sumWeight = 0;
		sumPrice = 0;
		log.info("tote contains {} products:", temp.size());
		for (Product thisp : temp) {
			log.info("{}", thisp);
			sumIds += thisp.id();
			sumVolume += thisp.volume();
			sumWeight += thisp.weight();
			sumPrice += thisp.price();
		}
		log.info("sumOfIds [{}] price [{}] volume [{}/{}] weight [{}]", sumIds,
				sumPrice, sumVolume, TOTE_CAPACITY, sumWeight);

		Set<Product> tote = new HashSet<>();
		boolean isOtherProductAdded = false;
		SortedSet<Product> sameVolume;
		for (Product thisp : temp) {
			isOtherProductAdded = false;
			sameVolume = orderByVolumeAndWeight.get(thisp.volume());
			for (Product otherp : sameVolume) {
				if (otherp.weight() < thisp.weight()
						&& otherp.price() >= thisp.price()
						&& !temp.contains(otherp)) {
					isOtherProductAdded = true;
					log.info("isOtherProductAdded [true] id [{}]", otherp.id());
					tote.add(otherp);
					break;
				}
			}
			if (!isOtherProductAdded) {
				log.debug("isOtherProductAdded [false] id [{}]", thisp.id());
				tote.add(thisp);
			}
		}
		sumIds = 0;
		sumVolume = 0;
		sumWeight = 0;
		sumPrice = 0;
		log.info("tote contains {} products:", tote.size());
		for (Product thisp : tote) {
			log.info("{}", thisp);
			sumIds += thisp.id();
			sumVolume += thisp.volume();
			sumWeight += thisp.weight();
			sumPrice += thisp.price();
		}
		log.info("sumOfIds [{}] price [{}] volume [{}/{}] weight [{}]", sumIds,
				sumPrice, sumVolume, TOTE_CAPACITY, sumWeight);
	}

	private static Products products() throws IOException {
		final int nTokens = 6;
		int size;
		String line;
		List<String> tokens;
		TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume = TreeMultimap.create(
				Ordering.natural()
					.reverse(), Product.ORDER_BY_VOLUME);
		TreeMultimap<Integer, Product> orderByVolumeAndWeight = TreeMultimap.create(
				Ordering.natural(), Product.ORDER_BY_WEIGHT);
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
				orderByPriceToVolumeRatioDescendingAndVolume.put(
						p.priceToVolumeRatio(), p);
				orderByVolumeAndWeight.put(p.volume(), p);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return new Products(orderByPriceToVolumeRatioDescendingAndVolume,
				orderByVolumeAndWeight);
	}

}
