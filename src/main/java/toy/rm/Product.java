package toy.rm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

public final class Product {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Product.class);
	protected static final Ordering<Product> ORDER_BY_VOLUME = Ordering.natural()
		.nullsFirst()
		.onResultOf(new Function<Product, Integer>() {
			public Integer apply(Product p) {
				return p.volume();
			}
		});
	protected static final Ordering<Product> ORDER_BY_VOLUME_DESCENDING = ORDER_BY_VOLUME.reverse();
	protected static final Ordering<Product> ORDER_BY_WEIGHT = Ordering.natural()
		.nullsFirst()
		.onResultOf(new Function<Product, Integer>() {
			public Integer apply(Product p) {
				return p.weight();
			}
		});
	protected static final Ordering<Product> ORDER_BY_WEIGHT_DESCENDING = ORDER_BY_WEIGHT.reverse();
	protected static final Ordering<Product> ORDER_BY_PRICE_TO_VOLUME_RATIO = Ordering.natural()
		.nullsFirst()
		.onResultOf(new Function<Product, Double>() {
			public Double apply(Product p) {
				return p.priceToVolumeRatio();
			}
		});
	protected static final Ordering<Product> ORDER_BY_PRICE_TO_VOLUME_RATIO_DESCENDING = ORDER_BY_PRICE_TO_VOLUME_RATIO.reverse();
	private int id = 0;
	private int price = 0;
	private int length = 0;
	private int width = 0;
	private int height = 0;
	private int weight = 0;

	private Product(Builder builder) {
		id = builder.id;
		price = builder.price;
		length = builder.length;
		width = builder.width;
		height = builder.height;
		weight = builder.weight;
	}

	protected int volume() {
		return length * width * height;
	}

	protected double priceToVolumeRatio() {
		return (double) price / volume();
	}

	protected int id() {
		return id;
	}

	protected int price() {
		return price;
	}

	protected int length() {
		return length;
	}

	protected int width() {
		return width;
	}

	protected int height() {
		return height;
	}

	protected int weight() {
		return weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + id;
		result = prime * result + length;
		result = prime * result + price;
		result = prime * result + weight;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		if (height != other.height) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (length != other.length) {
			return false;
		}
		if (price != other.price) {
			return false;
		}
		if (weight != other.weight) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id [");
		sb.append(id);
		sb.append("] price [");
		sb.append(price);
		sb.append("] length [");
		sb.append(length);
		sb.append("] width [");
		sb.append(width);
		sb.append("] height [");
		sb.append(height);
		sb.append("] weight [");
		sb.append(weight);
		sb.append("]");
		return sb.toString();
	}

	public static class Builder {
		private int id = 0;
		private int price = 0;
		private int length = 0;
		private int width = 0;
		private int height = 0;
		private int weight = 0;

		protected Builder() {
			// no-op
		}

		protected Product build() {
			return new Product(this);
		}

		protected Builder id(int id) {
			this.id = id;
			return this;
		}

		protected Builder price(int price) {
			this.price = price;
			return this;
		}

		protected Builder length(int length) {
			this.length = length;
			return this;
		}

		protected Builder width(int width) {
			this.width = width;
			return this;
		}

		protected Builder height(int height) {
			this.height = height;
			return this;
		}

		protected Builder weight(int weight) {
			this.weight = weight;
			return this;
		}
	}

}
