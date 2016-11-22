package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Facebook interview question round 1 (Nov 2016)
 * --------------------------------------------------
 Question:  Given a sequence of positive integers A and an integer T, return whether there is a *continuous sequence* of A that sums up to exactly T
 Example
 [23, 5, 4, 7, 2, 11], 20. Return True because 7 + 2 + 11 = 20
 [1, 3, 5, 23, 2], 8. Return True  because 3 + 5 = 8
 [1, 3, 5, 23, 2], 7 Return False because no sequence in this array adds up to 7

 Note: We are looking for an O(N) solution. There is an obvious O(N^2) solution which is a good starting point but is not the final solution we are looking for.
 ----------------------------------------------------
 */

public final class FacebookQ1 {
	private static final Logger log = LoggerFactory.getLogger(FacebookQ1.class);

	private FacebookQ1() {
		// Not meant to be instantiated
	}

	public static boolean containsSequenceSum(int[] a, int t) {
		if (a == null || a.length == 0) {
			throw new IllegalArgumentException();
		}
		if (t < 1) {
			throw new IllegalArgumentException();
		}
		int sum = 0;
		int sumStartAt = 0;
		for (int i = 0; i < a.length; i++) {
			log.debug("i [{}], sum[{}], sumStartAt [{}]", i, sum, sumStartAt);
			if (a[i] == t) {
				return true;
			}
			if (sum == 0) {
				sumStartAt = i;
			}
			sum += a[i];
			if (sum == t) {
				return true;
			}
			if (sum < t) {
				continue;
			}
			while (sum > t) {
				sum -= a[sumStartAt];
				sumStartAt++;
			}
			if (sum == t) {
				return true;
			}
		}
		return false;
	}

}
