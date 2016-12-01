package toy.pm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

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
