package toy.pm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find the average of transactions' values from the traders living in Beijing.
 * 
 */
public final class AverageTransactionValueOfBeijingTraders {
	private static final Logger log = LoggerFactory.getLogger(AverageTransactionValueOfBeijingTraders.class);

	private AverageTransactionValueOfBeijingTraders() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		List<Trader> traders = PmApi.traders();
		if (traders.isEmpty()) {
			System.out.println("No data");
			System.exit(0);
		}
		List<Transaction> txns = PmApi.transactions();
		if (txns.isEmpty()) {
			System.out.println("No data");
			System.exit(0);
		}
		// Use a HashSet to store the trader ids.
		// The average case takes theta(1) time for add, remove and, more
		// importantly for this use case, contains.
		// The average case should occur with high probability because the
		// trader id is unique.
		// Loop transactions and check whether the trader id is contained in the
		// set.
		// The HashSet is backed by a HashMap (hash table) which takes O(n)
		// space.
		// The HashSet also discards duplicate ids.
		Set<String> beijingTraderIds = new HashSet<>(traders.size());
		for (Trader t : traders) {
			if (t.city()
				.toLowerCase()
				.equals("beijing")) {
				beijingTraderIds.add(t.id());
			}
		}
		// Number of transactions
		int n = 0;
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal val;
		for (Transaction txn : txns) {
			if (beijingTraderIds.contains(txn.traderId())) {
				val = txn.value();
				sum = sum.add(val);
				n++;
				log.debug("n [{}], val [{}], sum [{}]", n, val, sum);
			}
		}
		BigDecimal avg = sum.divide(new BigDecimal(n));
		System.out.println("Average txn value: " + avg);
	}

}
