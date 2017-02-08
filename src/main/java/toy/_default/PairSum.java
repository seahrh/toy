package toy._default;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find a pair of elements from an array whose sum equals a given number. The
 * array can contain any integer, including zero and negative numbers. The array
 * elements are unique.
 * 
 */
public final class PairSum {
	private static final Logger log = LoggerFactory.getLogger(PairSum.class);

	private PairSum() {
		// Not meant to be instantiated
	}

	/**
	 * Convert the array into a hashtable where the key is the value of the
	 * array element, and the value is the index of the array element. Makes two
	 * passes through the array, first to build the hashtable, second to check
	 * whether the other sum operand exists.
	 * <p>
	 * This takes O(n) time and O(n) space.
	 * 
	 * @param arr
	 * @param sum
	 * @return
	 */
	public static List<Integer> indexOfSumOperands(int[] arr, int sum) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		Map<Integer, Integer> valueToIndex = new HashMap<>();
		for (int i = 0; i < len; i++) {
			valueToIndex.put(arr[i], i);
		}
		// Since array elements are unique, pairs of indexes that give a certain sum
		// are also unique.
		// Hence use a list that discards a duplicate pair of indexes that is
		// inserted in reverse order i.e. (j, i).
		Set<Integer> pairs = new LinkedHashSet<>();
		int target;
		Integer j;
		for (int i = 0; i < len; i++) {
			target = sum - arr[i];
			j = valueToIndex.get(target);
			// The target must not be self, so check i != j
			if (j != null && i != j) {				
				pairs.add(i);
				pairs.add(j);
			}
		}
		log.debug("pairs={}", pairs);
		return new ArrayList<Integer>(pairs);
	}

}
