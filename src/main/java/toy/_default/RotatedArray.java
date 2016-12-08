package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A sorted array has been rotated so that the elements might appear in the
 * order 3 4 5 6 7 1 2.
 * 
 */
public class RotatedArray {
	private static final Logger log = LoggerFactory.getLogger(RotatedArray.class);

	private RotatedArray() {
		// Not meant to be instantiated
	}

	public static int min(int[] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		return minBinarySearch(arr, 0, arr.length - 1);
	}

	/**
	 * Find minimum element in a sorted but rotated array.
	 * 
	 * @param arr
	 * @param begin
	 *            begin index of the search range
	 * @param end
	 *            end index of the search range
	 * @return
	 */
	private static int minBinarySearch(int[] arr, int begin, int end) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		if (begin < 0 || begin >= len) {
			log.error("begin index out of bounds. [{}]", begin);
			throw new IllegalArgumentException();
		}
		if (end < 0 || end >= len) {
			log.error("end index out of bounds. [{}]", end);
			throw new IllegalArgumentException();
		}
		// 1st base case: only 1 element in search range
		if (begin >= end) {
			return begin;
		}
		// Avoid integer overflow
		int mid = (end - begin) / 2 + begin;
		log.debug("mid [{}]", mid);
		// Include the middle element in search range!
		// Because it could be the min.
		if (arr[mid] < arr[0]) {
			return minBinarySearch(arr, begin, mid);
		}
		// Search the right partition if
		// mid element is greater than or equals begin element.
		// Exclude the middle element in search range.
		return minBinarySearch(arr, mid + 1, end);
	}

}
