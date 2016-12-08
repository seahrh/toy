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
	
	public static int binarySearch(int[] arr, int key) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int minIndex = min(arr);
		return binarySearch(arr, 0, arr.length - 1, key, minIndex);
	}

	/**
	 * Find element in a sorted but rotated array.
	 * This algorithm still takes O(log n) time:
	 * - finding the minimum takes O(log n) time,
	 * - binary search also takes O(log n) time.
	 * 
	 * @param arr
	 * @param begin
	 *            begin index of the search range, inclusive
	 * @param end
	 *            end index of the search range, inclusive
	 * @param minIndex
	 *            index of the minimum element
	 * @return index of key element, otherwise -1 if not found
	 */
	private static int binarySearch(int[] arr, int begin, int end, int key,
			int minIndex) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		if (begin < 0) {
			log.error("begin index out of bounds. [{}]", begin);
			throw new IllegalArgumentException();
		}
		if (end >= len) {
			log.error("end index out of bounds. [{}]", end);
			throw new IllegalArgumentException();
		}
		if (minIndex < 0 || minIndex >= len) {
			log.error("minIndex out of bounds. [{}]", minIndex);
			throw new IllegalArgumentException();
		}
		// 1st base case: search range is empty
		if (begin > end) {
			return -1;
		}
		int mid = (end - begin) / 2 + begin;
		int midv = arr[mid];
		// 2nd base case: key found
		if (midv == key) {
			return mid;
		}
		if (key < midv) {
			int result = binarySearch(arr, begin, mid - 1, key, minIndex);
			if (result == -1 && minIndex > mid) {
				result = binarySearch(arr, minIndex, end, key, minIndex);
			}
			return result;
		}
		return binarySearch(arr, mid + 1, end, key, minIndex);
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
	 *            begin index of the search range, inclusive
	 * @param end
	 *            end index of the search range, inclusive
	 * @return index of minimum element
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
		if (begin < 0) {
			log.error("begin index out of bounds. [{}]", begin);
			throw new IllegalArgumentException();
		}
		if (end >= len) {
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
