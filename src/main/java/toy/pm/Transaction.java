package toy.pm;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class Transaction implements Comparable<Transaction>, Serializable {
	private static final Logger log = LoggerFactory.getLogger(Transaction.class);
	private static final long serialVersionUID = 1L;
	private static Gson gson;

	private DateTime timestamp = null;
	private BigDecimal value = null;
	private String traderId = null;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(DateTime.class,
				new JsonDeserializer<DateTime>() {
					public DateTime deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {
						return new DateTime(json.getAsJsonPrimitive()
							.getAsLong() * 1000L, DateTimeZone.UTC);
					}
				});
		builder.registerTypeAdapter(BigDecimal.class,
				new JsonDeserializer<BigDecimal>() {
					public BigDecimal deserialize(JsonElement json,
							Type typeOfT, JsonDeserializationContext context)
							throws JsonParseException {
						return new BigDecimal(json.getAsJsonPrimitive()
							.getAsString());
					}
				});
		gson = builder.create();
	}

	public Transaction(DateTime timestamp, BigDecimal value, String traderId) {
		timestamp(timestamp);
		value(value);
		traderId(traderId);
	}

	public static List<Transaction> fromJsonToList(String json) {
		if (json == null || json.isEmpty()) {
			log.error("json must not be null or empty string");
			throw new IllegalArgumentException();
		}
		final Type listType = new TypeToken<ArrayList<Transaction>>() {
		}.getType();
		return gson.fromJson(json, listType);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(value);
		sb.append(", ");
		sb.append(timestamp);
		sb.append(", ");
		sb.append(traderId);
		return sb.toString();
	}

	/*
	 * Instances are ordered by value.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Transaction other) {
		return value.compareTo(other.value());
	}

	public DateTime timestamp() {
		return timestamp;
	}

	public void timestamp(DateTime timestamp) {
		if (timestamp == null) {
			log.error("timestamp must not be null");
			throw new IllegalArgumentException();
		}
		this.timestamp = timestamp;
	}

	public BigDecimal value() {
		return value;
	}

	public void value(BigDecimal value) {
		if (value == null) {
			log.error("value must not be null");
			throw new IllegalArgumentException();
		}
		this.value = value;
	}

	public String traderId() {
		return traderId;
	}

	public void traderId(String traderId) {
		if (traderId == null || traderId.isEmpty()) {
			log.error("traderId must not be null or empty string");
			throw new IllegalArgumentException();
		}
		this.traderId = traderId.toLowerCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result
				+ ((traderId == null) ? 0 : traderId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (!(obj instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) obj;
		if (timestamp == null) {
			if (other.timestamp != null) {
				return false;
			}
		} else if (!timestamp.equals(other.timestamp)) {
			return false;
		}
		if (traderId == null) {
			if (other.traderId != null) {
				return false;
			}
		} else if (!traderId.equals(other.traderId)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
