package toy._default;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class StringPermutation {
	private static final Logger log = LoggerFactory.getLogger(StringPermutationTest.class);

	private StringPermutation() {
		// Not meant to be instantiated
	}

	/**
	 * Merge char c into every permutation of string s.
	 * This is a recursive algorithm that takes O(n * n!) time and O(n) space.
	 * 
	 * @param s
	 * @param c
	 * @return
	 */
	private static Set<String> merge(String s, char c) {
		if (s == null) {
			log.error("string must not be null");
			throw new IllegalArgumentException();
		}
		int len = s.length();
		if (len == 0) {
			log.error("string must not be empty");
			throw new IllegalArgumentException();
		}
		// All permutations of string s
		Set<String> perms;
		// Base case: length 1 string
		if (len == 1) {
			perms = Sets.newHashSet(s);
		} else {
			String sub = s.substring(0, len - 1);
			char last = s.charAt(len - 1);
			perms = merge(sub, last);
		}
		Set<String> ret = new HashSet<>(len * len);
		StringBuilder sb;
		for (String perm : perms) {
			len = perm.length();
			// prepend c
			sb = new StringBuilder();
			sb.append(String.valueOf(c));
			sb.append(perm);
			ret.add(sb.toString());
			// stick c into every intermediate position
			for (int i = 1; i < len; i++) {
				sb = new StringBuilder();
				sb.append(perm.substring(0, i));
				sb.append(c);
				sb.append(perm.substring(i));
				ret.add(sb.toString());
			}
			// append c
			sb = new StringBuilder(perm);
			sb.append(String.valueOf(c));
			ret.add(sb.toString());
		}
		return ret;
	}

	/**
	 * Get all permutations of a string
	 * 
	 * @param s
	 *            string must not be null or empty. All characters must be
	 *            unique.
	 * @return set of permutations
	 */
	public static Set<String> permutate(String s) {
		if (s == null) {
			log.error("string must not be null");
			throw new IllegalArgumentException();
		}
		int len = s.length();
		if (len == 0) {
			log.error("string must not be empty");
			throw new IllegalArgumentException();
		}
		if (len == 1) {
			return Sets.newHashSet(s);
		}
		String sub = s.substring(0, len - 1);
		char last = s.charAt(len - 1);
		return merge(sub, last);
	}

}
