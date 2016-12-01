package toy.pm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.TreeMultimap;

/**
 * Find all traders from Singapore and sort them by name.
 * 
 */
public final class SingaporeTradersOrderByName {

	private SingaporeTradersOrderByName() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		List<Trader> traders = PmApi.traders();
		if (traders.isEmpty()) {
			System.out.println("No data");
			System.exit(0);
		}
		// A regular TreeMap sorts the entries by key and there can only be at
		// most one value mapped to a key.
		// A TreeMultimap is different from a TreeMap because it allows more
		// than one value to map to the same key.
		// Assume duplicate names exist, hence a TreeMultimap is used.
		// Like TreeMap, the keys of a TreeMultimap are sorted in a red-black
		// tree which takes O(n) space.
		// This takes O(log(n)) time for the containsKey, get, put and remove
		// operations.
		TreeMultimap<String, Trader> sortedByName = TreeMultimap.create();
		for (Trader t : traders) {
			if (t.city()
				.toLowerCase()
				.equals("singapore")) {
				sortedByName.put(t.name(), t);
			}
		}
		for (Map.Entry<String, Trader> entry : sortedByName.entries()) {
			System.out.println(entry.getValue());
		}
	}

}
