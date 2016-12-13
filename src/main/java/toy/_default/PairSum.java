package toy._default;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
public class PairSum {
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
		List<Integer> ret = new ArrayList<>();
		int target;
		Integer j;
		Set<Integer> foundIndices = new HashSet<>();
		for (int i = 0; i < len; i++) {
			target = sum - arr[i];
			j = valueToIndex.get(target);
			// The target must not be self, so check i != j
			if (j != null && i != j && !foundIndices.contains(i) && !foundIndices.contains(j)) {
				foundIndices.add(i);
				foundIndices.add(j);
				ret.add(i);
				ret.add(j);
			}
		}
		log.debug("ret={}", ret);
		return ret;
	}

}
