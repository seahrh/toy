package toy.pm;

import java.io.IOException;
import java.util.List;

public final class HighestValueTransaction {

	private HighestValueTransaction() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		List<Transaction> txns = PmApi.transactions();
		if (txns.isEmpty()) {
			System.out.println("No data");
			System.exit(0);
		}
		Transaction maxValueTxn = txns.get(0);
		for (Transaction txn : txns) {
			if (txn.value().compareTo(maxValueTxn.value()) > 0) {
				maxValueTxn = txn;
			}
		}
		System.out.println(maxValueTxn);
	}

}
