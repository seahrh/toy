package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BinarySearch {
	private static final Logger log = LoggerFactory.getLogger(BinarySearch.class);

	private BinarySearch() {
		// Not meant to be instantiated
	}
	
	/**
	 * Binary search to get index of key.
	 * If key is not found, return index of nearest value.
	 * 
	 * @param arr
	 * @param begin
	 * @param end
	 * @param key
	 * @param nearest
	 * @return
	 */
	private static int nearest(int[] arr, int begin, int end, int key, int nearest) {
		int len = arr.length;
		if (begin < 0) {
			log.error("begin index must not be less than zero");
			throw new IllegalArgumentException();
		}
		if (end >= len) {
			log.error("end index must not be greater than last index [{}]", len - 1);
			throw new IllegalArgumentException();
		}
		if (nearest < 0 || nearest >= len) {
			log.error("index of nearest value out of bounds [{}]", nearest);
			throw new IllegalArgumentException();
		}
		// 1st base case: key not found
		if (begin > end) {
			return nearest;
		}
		// Avoid integer overflow
		int mid = (end - begin) / 2 + begin;
		int vmid = arr[mid];
		// 2nd base case: key found
		if (vmid == key) {
			return mid;
		}
		int vnearest = arr[nearest];
		if (Math.abs(key - vmid) < Math.abs(key - vnearest)) {
			nearest = mid;
		}
		if (key < vmid) {	
			return nearest(arr, begin, mid - 1, key, nearest);
		}
		return nearest(arr, mid + 1, end, key, nearest);
	}
	
	public static int nearest(int[] arr, int key) {
		if (arr == null || arr.length == 0) {
			log.error("array must not be null or empty");
			throw new IllegalArgumentException();
		}
		// Nearest value initialized as the 1st element
		return nearest(arr, 0, arr.length - 1, key, 0);
	}

	private static int search(int[] arr, int begin, int end, int key) {
		if (begin < 0) {
			log.error("begin index must not be less than zero");
			throw new IllegalArgumentException();
		}
		if (end >= arr.length) {
			log.error("end index must not be greater than last index [{}]", arr.length - 1);
			throw new IllegalArgumentException();
		}
		// 1st base case: key not found
		if (begin > end) {
			return -1;
		}
		// Avoid integer overflow
		int mid = (end - begin) / 2 + begin;
		int vmid = arr[mid];
		// 2nd base case: key found
		if (vmid == key) {
			return mid;
		}
		if (key < vmid) {
			return search(arr, begin, mid - 1, key);
		}
		return search(arr, mid + 1, end, key);
	}

	public static int search(int[] arr, int key) {
		if (arr == null || arr.length == 0) {
			log.error("array must not be null or empty");
			throw new IllegalArgumentException();
		}
		return search(arr, 0, arr.length - 1, key);
	}

}
