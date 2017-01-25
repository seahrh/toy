package toy.bx;

import static toy.util.StringUtil.concat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;

public final class ItemCf {
	private static final Logger log = LoggerFactory.getLogger(ItemCf.class);

	private ItemCf() {
		// Not meant to be instantiated
	}

	protected static Optional<Integer> predict(String uid, String isbn,
			ImmutableTable<String, String, Integer> ratingTable,
			Map<String, Float> simMatrix) {
		ImmutableMap<String, Integer> ratings = ratingTable.column(uid);
		if (ratings.isEmpty()) {
			// User has not rated any items, so cannot make prediction.
			return Optional.absent();
		}
		Integer ret;
		float p;
		Integer rating = ratings.get(isbn);
		if (rating != null) {
			log.warn(
					"user has already rated this item, so no prediction was made.\nuid={} isbn={}",
					uid, isbn);
			return Optional.absent();
		}
		String ratedIsbn;
		float nu = 0;
		float de = 0;
		Float sim;
		for (Map.Entry<String, Integer> entry : ratings.entrySet()) {
			ratedIsbn = entry.getKey();
			rating = entry.getValue();
			sim = simMatrix.get(pairKey(isbn, ratedIsbn));
			if (sim == null) {
				log.debug(
						"similarity score is not available for item pair={},{}",
						isbn, ratedIsbn);
				continue;
			}
			nu += sim * rating;
			de += sim;
		}
		p = nu / de;
		if (p == 0) {
			log.warn("predicted rating must be greater than zero");
			return Optional.absent();
		}
		ret = Math.round(p);
		if (ret == 0) {
			ret = 1;
		}
		return Optional.of(ret);
	}

	protected static String pairKey(String isbn1, String isbn2) {
		final Character separator = ' ';
		if (isbn1.compareTo(isbn2) <= 0) {
			return concat(isbn1, separator, isbn2);
		}
		return concat(isbn2, separator, isbn1);
	}

}
