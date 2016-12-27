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
		.onResultOf(new Function<Product, Double>() {
			public Double apply(Product p) {
				return p.priceToVolumeWeightRatio();
			}
		});
	protected static final Ordering<Product> ORDER_BY_VOLUME_DESCENDING = ORDER_BY_VOLUME.reverse();
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

	protected double priceToVolumeWeightRatio() {
		return 1000000D * price / (volume() * weight);
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
