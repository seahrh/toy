package toy.rm;

import com.google.common.collect.TreeMultimap;

/**
 * Data object wrapping the data structures obtained from reading the products
 * file in 1 pass.
 * 
 */
public final class Products {
	/**
	 * Hashtable where products are sorted by price to volume ratio descending, then volume ascending.
	 */
	private TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume = null;
	/**
	 * Hashtable where products are sorted by volume ascending, then weight ascending.
	 */
	private TreeMultimap<Integer, Product> orderByVolumeAndWeight = null;

	protected Products(
			TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume,
			TreeMultimap<Integer, Product> orderByVolumeAndWeight) {
		this.orderByPriceToVolumeRatioDescendingAndVolume = orderByPriceToVolumeRatioDescendingAndVolume;
		this.orderByVolumeAndWeight = orderByVolumeAndWeight;
	}

	protected TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume() {
		return orderByPriceToVolumeRatioDescendingAndVolume;
	}

	protected TreeMultimap<Integer, Product> orderByVolumeAndWeight() {
		return orderByVolumeAndWeight;
	}

}
