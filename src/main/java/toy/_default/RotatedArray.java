package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A sorted array has been rotated so that the elements might appear in the
 * order 3 4 5 6 7 1 2. Assume no duplicates (otherwise a O(log n) time solution
 * is not achievable).
 * <p>
 * First, we know that it is a sorted array that’s been rotated. Although we do
 * not know where the rotation pivot is, there is a property we can take
 * advantage of. Here, we make an observation that a rotated array can be
 * classified as two sub-array that is sorted (i.e., 4 5 6 7 0 1 2 consists of
 * two sub-arrays 4 5 6 7 and 0 1 2.
 * 
 */
public class RotatedArray {
	private static final Logger log = LoggerFactory.getLogger(RotatedArray.class);

	private RotatedArray() {
		// Not meant to be instantiated
	}

	/**
	 * Find element in a sorted but rotated array with no duplicates.
	 * <p>
	 * Performs a modified version of binary search, so search takes O(lg n) time.
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
	public static int rotatedBinarySearch(int[] arr, int key) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		int lo = 0;
		int hi = len - 1;
		int mid;
		while (lo <= hi) {
			mid = (hi - lo) / 2 + lo;
			if (arr[mid] == key) {
				return mid;
			}
			// Left partition is sorted
			if (arr[lo] <= arr[mid]) {
				// Key is inside the boundaries of the left partition
				if (arr[lo] <= key && key < arr[mid]) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			} else {
				// Right partition is sorted
				// Key is inside the boundaries of the right partition
				if (arr[mid] < key && key <= arr[hi]) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
		}
		return -1;
	}

	/**
	 * Find minimum element in a sorted but rotated array.
	 * <p>
	 * Solution is a modified version of binary search. This takes O(log n) time
	 * if there are no duplicates.
	 * 
	 * @param arr
	 * @return index of minimum element
	 */
	public static int indexOfMin(int[] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		int lo = 0;
		int hi = len - 1;
		int mid;
		// Look for min in the unsorted partition
		while (arr[lo] > arr[hi]) {
			mid = (hi - lo) / 2 + lo;
			// Right partition is unsorted
			if (arr[mid] > arr[hi]) {
				lo = mid + 1;
			} else {
				hi = mid;
			}
		}
		return lo;
	}

}
