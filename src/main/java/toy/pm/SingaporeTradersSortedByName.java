package toy.pm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.TreeMultimap;

public final class SingaporeTradersSortedByName {

	private SingaporeTradersSortedByName() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) throws IOException {
		List<Trader> traders = PmApi.traders();
		if (traders.isEmpty()) {
			System.out.println("No data");
			System.exit(0);
		}
		TreeMultimap<String, Trader> sortedByName = TreeMultimap.create();
		for (Trader t : traders) {
			if (t.city().toLowerCase().equals("singapore")) {
				sortedByName.put(t.name(), t);
			}
		}
		for (Map.Entry<String, Trader> entry : sortedByName.entries()) {
			System.out.println(entry.getValue());
		}
	}

}
