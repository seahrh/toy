package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyStrings {
	private static final Logger log = LoggerFactory.getLogger(MyStrings.class);

	private MyStrings() {
		// Not meant to be instantiated
	}

	/**
	 * Q1.3
	 * <p>
	 * Design an algorithm and write code to remove the duplicate characters in
	 * a string without using any additional buffer. NOTE: One or two additional
	 * variables are fine. An extra copy of the array is not.
	 * <p>
	 * This takes O(n) time and O(1) space.
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
