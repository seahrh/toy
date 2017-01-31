package toy.bx;

import static toy.util.StringUtil.concat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;

/**
 * Helper class for item-based collaborative filtering on the book crossing
 * dataset.
 * 
 */
public final class ItemCf {
	private static final Logger log = LoggerFactory.getLogger(ItemCf.class);

	private ItemCf() {
		// Not meant to be instantiated
	}

	/**
	 * The similarity scores are used to generate predictions using a weighted
	 * average, similar to the procedure used in user–based CF. Recommendations
	 * are then generated by picking the candidate items with the highest
	 * predictions.
	 * <p>
	 * A neighbourhood S refers to the items most similar to the item being
	 * predicted. Unlike the prediction formula in Ekstrand et al (2010), there
	 * is no upper bound on the size of S. This is because the rating matrix is
	 * sparse, i.e. there are few ratings per user. This is also known as the
	 * cold start problem.
	 * <p>
	 * Instead S requires a minimum size, as specified by
	 * <code>minRatings</code>. This improves accuracy, as the cost of making
	 * fewer predictions.
	 * <p>
	 * Does not return a prediction under the following conditions:
	 * <ul>
	 * <li>user does not have a sufficient number of ratings (as specified by
	 * <code>minRating</code>).</li>
	 * <li>item does not have any similar items</li>
	 * </ul>
	 * 
	 * @param uid
	 *            user id for which prediction is made
	 * @param isbn
	 *            book id for which prediction is made
	 * @param ratingTable
	 *            rating matrix where rows are books and column are users
	 * @param simMatrix
	 *            item-item similarity matrix (books)
	 * @param minRatings
	 *            minimum number of ratings required to make a prediction
	 * @return Optionally returns a predicted rating as integer in the range
	 *         [1,10] inclusive.
	 */
	protected static Optional<Integer> predict(String uid, String isbn,
			ImmutableTable<String, String, Integer> ratingTable,
			Map<String, Float> simMatrix, int minRatings) {
		ImmutableMap<String, Integer> ratings = ratingTable.column(uid);
		if (ratings.isEmpty()) {
			// User has not rated any items, so cannot make prediction.
			return Optional.absent();
		}
		if (ratings.size() < minRatings) {
			// User has rated too few items to make an accurate prediction.
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
		int count = 0;
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
			count++;
		}
		if (count < minRatings) {
			// Too few rated items to make an accurate prediction,
			// some items were dropped because the similarity score is not
			// available.
			return Optional.absent();
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

	/**
	 * Returns a key to lookup the similarity score in a hash table. The order
	 * of arguments does not matter because the book ids are sorted before
	 * concatenating them to give the key.
	 * 
	 * @param isbn1
	 *            first book id
	 * @param isbn2
	 *            second book id
	 * @return key to lookup similarity score
	 */
	protected static String pairKey(String isbn1, String isbn2) {
		final Character separator = ' ';
		if (isbn1.compareTo(isbn2) <= 0) {
			return concat(isbn1, separator, isbn2);
		}
		return concat(isbn2, separator, isbn1);
	}

}
