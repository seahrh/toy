package toy.rm;

import com.google.common.collect.TreeMultimap;

public final class Products {
	private TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume = null;
	private TreeMultimap<Integer, Product> orderByVolumeAndWeight = null;
	
	protected Products(TreeMultimap<Double, Product> orderByPriceToVolumeRatioDescendingAndVolume, 
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
