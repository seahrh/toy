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
		Products products = products();
		Set<Product> tote = pickHighPriceToVolumeRatio(products);
		debug(tote);
		tote = pickLowWeight(products, tote);
		debug(tote);
	}

	/**
	 * Optimise the picked products by picking another product that has a lower
	 * weight for the same volume, without sacrificing the dollar value of the
	 * tote.
	 * 
	 * @param products
	 *            must not be null
	 * @param picked
	 *            must not be null or empty
	 * @return picked items optimised by weight
	 */
	private static Set<Product> pickLowWeight(Products products,
			Set<Product> picked) {
		if (products == null) {
			log.error("products must not be null");
			throw new IllegalArgumentException();
		}
		if (picked == null || picked.isEmpty()) {
			log.error("picked products must not be null or empty");
			throw new IllegalArgumentException();
		}
		TreeMultimap<Integer, Product> orderByVolumeAndWeight = products.orderByVolumeAndWeight();
		Set<Product> ret = new HashSet<>();
		boolean isOtherProductAdded = false;
		SortedSet<Product> sameVolume;
		for (Product thisp : picked) {
			isOtherProductAdded = false;
			sameVolume = orderByVolumeAndWeight.get(thisp.volume());
			for (Product otherp : sameVolume) {
				if (otherp.weight() < thisp.weight()
						&& otherp.price() >= thisp.price()
						&& !picked.contains(otherp)) {
					isOtherProductAdded = true;
					log.info("isOtherProductAdded [true] id [{}]", otherp.id());
					ret.add(otherp);
					break;
				}
			}
			if (!isOtherProductAdded) {
				log.debug("isOtherProductAdded [false] id [{}]", thisp.id());
				ret.add(thisp);
			}
		}
		return ret;
	}

	/**
	 * Get hashtable where products are sorted by price-to-volume ratio
	 * descending. Pick products with the highest price-to-volume ratio until
	 * the tote is full.
	 * 
	 * @param products
	 *            must not be null
	 * @return picked products in the tote
	 */
	private static Set<Product> pickHighPriceToVolumeRatio(Products products) {
		if (products == null) {
			log.error("products must not be null");
			throw new IllegalArgumentException();
		}
		Set<Product> ret = new HashSet<>();
		TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume = products.orderByPriceToVolumeRatioDescendingAndVolume();
		Product p;
		Double priceToVolumeRatio;
		int sumVolume = 0;
		int volume;
		for (Map.Entry<Double, Product> entry : orderByPriceToVolumeRatioDescendingAndVolume.entries()) {
			p = entry.getValue();
			priceToVolumeRatio = entry.getKey();
			log.debug("priceToVolumeRatio [{}] {}", priceToVolumeRatio, p);
			volume = p.volume();
			// Keep looking for a product that will fit in the remaining
			// capacity
			if (sumVolume + volume > TOTE_CAPACITY) {
				continue;
			}
			sumVolume += volume;
			ret.add(p);
			if (sumVolume == TOTE_CAPACITY) {
				break;
			}
		}
		return ret;
	}

	/**
	 * Parse products file to objects
	 * 
	 * @return data object wrapping the products
	 * @throws IOException
	 */
	private static Products products() throws IOException {
		final int nTokens = 6;
		int size;
		String line;
		List<String> tokens;
		// Hashtable where products are sorted by price to volume ratio
		// descending, then volume ascending.
		// Use Guava's multimap because there could be multiple products with
		// the same price-to-volume ratio.
		TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume = TreeMultimap.create(
				Ordering.natural()
					.reverse(), Product.ORDER_BY_VOLUME);
		// Hashtable where products are sorted by volume ascending, then weight
		// ascending.
		// Use Guava's multimap because there could be multiple products with
		// the same volume.
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

	private static void debug(Set<Product> tote) {
		int sumIds = 0;
		int sumVolume = 0;
		int sumWeight = 0;
		int sumPrice = 0;
		log.debug("tote contains {} products:", tote.size());
		for (Product p : tote) {
			log.debug("{}", p);
			sumIds += p.id();
			sumVolume += p.volume();
			sumWeight += p.weight();
			sumPrice += p.price();
		}
		log.info("sumOfIds [{}] price [{}] volume [{}/{}] weight [{}]", sumIds,
				sumPrice, sumVolume, TOTE_CAPACITY, sumWeight);
	}

}
