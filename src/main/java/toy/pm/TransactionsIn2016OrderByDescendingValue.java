package toy.pm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

/**
 * Find all transactions in the year 2016 and sort them by value (high to small).
 *
 */
public final class TransactionsIn2016OrderByDescendingValue {

	private TransactionsIn2016OrderByDescendingValue() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		List<Transaction> txns = PmApi.transactions();
		if (txns.isEmpty()) {
			System.out.println("No data");
			System.exit(0);
		}
		// A regular TreeMap sorts the entries by key and there can only be at
		// most one value mapped to a key.
		// A TreeMultimap is different from a TreeMap because it allows more
		// than one value to map to the same key.
		// Assume duplicate values exist, hence a TreeMultimap is used.
		// Like TreeMap, the keys of a TreeMultimap are sorted in a red-black
		// tree which takes O(n) space.
		// This takes O(log(n)) time for the containsKey, get, put and remove
		// operations.
		TreeMultimap<BigDecimal, Transaction> orderByDescendingValue = TreeMultimap.create(
				Ordering.natural()
					.reverse(), Ordering.natural());
		for (Transaction txn : txns) {
			if (txn.timestamp()
				.getYear() == 2016) {
				orderByDescendingValue.put(txn.value(), txn);
			}
		}
		for (Map.Entry<BigDecimal, Transaction> entry : orderByDescendingValue.entries()) {
			System.out.println(entry.getValue());
		}
	}

}
