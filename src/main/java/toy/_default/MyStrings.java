package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyStrings {
	private static final Logger log = LoggerFactory.getLogger(MyStrings.class);

	private MyStrings() {
		// Not meant to be instantiated
	}

	/**
	 * Write a method that takes O(n) time to decide if two strings are anagrams
	 * or not. [CTCI Q1.4]
	 * <p>
	 * This takes O(n) time and O(1) space.
	 * <p>
	 * Assume charset is UTF-8 ASCII values only.
	 * 
	 * @param s
	 * @param t
	 * @return
	 */
	public static boolean isAnagram(String s, String t) {
		if (s == null) {
			log.error("string s must not be null");
			throw new IllegalArgumentException();
		}
		int slen = s.length();
		if (slen == 0) {
			log.error("string s must not be empty");
			throw new IllegalArgumentException();
		}
		if (t == null) {
			log.error("string t must not be null");
			throw new IllegalArgumentException();
		}
		int tlen = t.length();
		if (tlen == 0) {
			log.error("string t must not be empty");
			throw new IllegalArgumentException();
		}
		if (slen != tlen) {
			return false;
		}
		int[] counts = new int[128];
		int uniqueCount = 0;
		int tCompletedCount = 0;
		int codePoint;
		for (int i = 0; i < slen; i++) {
			codePoint = s.codePointAt(i);
			if (counts[codePoint] == 0) {
				uniqueCount++;
			}
			counts[codePoint]++;
		}
		for (int i = 0; i < tlen; i++) {
			codePoint = t.codePointAt(i);
			if (counts[codePoint] == 0) {
				return false;
			}
			counts[codePoint]--;
			if (counts[codePoint] == 0) {
				tCompletedCount++;
				if (tCompletedCount == uniqueCount) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Design an algorithm and write code to remove the duplicate characters in
	 * a string without using any additional buffer. NOTE: One or two additional
	 * variables are fine. An extra copy of the array is not. [CTCI Q1.3]
	 * <p>
	 * This takes O(n) time and O(1) space.
	 * <p>
	 * Assume charset is UTF-8 ASCII values only.
	 * 
	 * @param s
	 * @return
	 */
	public static String removeDuplicates(String s) {
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
			return s;
		}
		boolean[] isCodePointPresent = new boolean[256];
		StringBuilder sb = new StringBuilder();
		int codePoint;
		char c;
		for (int i = 0; i < len; i++) {
			c = s.charAt(i);
			codePoint = s.codePointAt(i);
			if (isCodePointPresent[codePoint]) {
				continue;
			}
			isCodePointPresent[codePoint] = true;
			sb.append(c);
		}
		return sb.toString();
	}

}
