package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SequenceSum {
	private static final Logger log = LoggerFactory.getLogger(SequenceSum.class);

	private SequenceSum() {
		// Not meant to be instantiated
	}

	/**
	 * You are given an array of integers (both positive and negative). Find the
	 * continuous sequence with the largest sum. Return the sum. [CTCI Q19.7]
	 * <p>
	 * EXAMPLE Input: {2, -8, 3, -2, 4, -10}
	 * <p>
	 * Output: 5 (i.e., {3, -2, 4} )
	 * <p>
	 * Assume subsequence cannot be empty if the array contains all negative
	 * integers.
	 * 
	 * @param arr
	 * @return
	 */
	public static int max(int[] arr) {
		if (arr == null || arr.length == 0) {
			log.error("array must not be null or empty");
			throw new IllegalArgumentException();
		}
		// Initialize max at lowest possible to handle negative numbers
		int max = Integer.MIN_VALUE;
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if (sum > max) {
				max = sum;
			} else if (sum < 0) {
				// If sum drops below zero, that subsequence will not
				// contribute to the maximal subsequence since max is reduced by
				// adding the negative sum.
				// If the array contains all negative numbers, sum is reset for
				// each element, effectively picking the smallest negative
				// number.
				sum = 0;
			}
		}
		return max;
	}

	/**
	 * Facebook interview question round 1 (Nov 2016)
	 * --------------------------------------------------
	 * <p>
	 * Question: Given a sequence of positive integers A and an integer T,
	 * return whether there is a *continuous sequence* of A that sums up to
	 * exactly T Example [23, 5, 4, 7, 2, 11], 20. Return True because 7 + 2 +
	 * 11 = 20 [1, 3, 5, 23, 2], 8. Return True because 3 + 5 = 8 [1, 3, 5, 23,
	 * 2], 7 Return False because no sequence in this array adds up to 7
	 * 
	 * Note: We are looking for an O(N) solution. There is an obvious O(N^2)
	 * solution which is a good starting point but is not the final solution we
	 * are looking for.
	 * <p>
	 * ----------------------------------------------------
	 * <p>
	 * O(n) solution based on @url
	 * https://www.careercup.com/question?id=6305076727513088
	 * 
	 * @param arr
	 * @param target
	 * @return
	 */
	public static boolean contains(int[] arr, int target) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		if (target < 1) {
			log.error("target sum must not be less than or equal to zero");
			throw new IllegalArgumentException();
		}
		// i is the begin index of the summation window
		// j is the end index of the summation window
		int sum = 0;
		int j = 0;
		for (int i = 0; i < len; ++i) {
			while (j < len && sum < target) {
				sum += arr[j];
				j++;
			}
			if (sum == target) {
				return true;
			}
			sum -= arr[i];
		}
		return false;
	}

}
