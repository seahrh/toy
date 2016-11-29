package toy._default;

public class BinarySearch {

	private BinarySearch() {
		// Not meant to be instantiated
	}

	public static int binarySearch(int[] arr, int begin, int end, int key) {
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}
		if (begin < 0) {
			throw new IllegalArgumentException();
		}
		if (end >= arr.length) {
			throw new IllegalArgumentException();
		}
		// 1st base case: key not found
		if (begin > end) {
			return -1;
		}
		// Avoid integer overflow
		int mid = (end - begin) / 2 + begin;
		int midv = arr[mid];
		// 2nd base case: key found
		if (midv == key) {
			return mid;
		}
		if (midv > key) {
			return binarySearch(arr, begin, mid - 1, key);
		}
		return binarySearch(arr, mid + 1, end, key);
	}

	public static int binarySearch(int[] arr, int key) {
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}
		return binarySearch(arr, 0, arr.length - 1, key);
	}

}
