package toy.pm;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transaction {
	private static final Logger log = LoggerFactory.getLogger(Transaction.class);
	private long timestamp = 0;
	private BigDecimal value = null;
	private String traderId = null;

	public Transaction(long timestamp, BigDecimal value, String traderId) {
		timestamp(timestamp);
		value(value);
		traderId(traderId);
	}

	public long timestamp() {
		return timestamp;
	}

	public void timestamp(long timestamp) {
		if (timestamp < 1) {
			log.error("timestamp must be greater than 0 [{}]", timestamp);
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
		this.traderId = traderId;
	}

}
