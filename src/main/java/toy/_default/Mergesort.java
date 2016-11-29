package toy._default;

public class Mergesort {

	private Mergesort() {
		// Not meant to be instantiated
	}
	
	public static void mergesort(int[] arr) {
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}
		// Base case: array len 1
		if (arr.length == 1) {
			return;
		}
		int mid = arr.length / 2;
		int[] left = new int[mid];
		int[] right = new int[arr.length - mid];
		System.arraycopy(arr, 0, left, 0, mid);
		System.arraycopy(arr, mid, right, 0, arr.length - mid);
		mergesort(left);
		mergesort(right);
		merge(left, right, arr);
	}
	
	private static void merge(int[] left, int[] right, int[] arr) {
		if (left == null || left.length == 0) {
			throw new IllegalArgumentException();
		}
		if (right == null || right.length == 0) {
			throw new IllegalArgumentException();
		}
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] <= right[j]) {
				arr[k++] = left[i++];
			} else {
				arr[k++] = right[j++];
			}
		}
		while (i < left.length) {
			arr[k++] = left[i++];
		}
		while (j < right.length) {
			arr[k++] = right[j++];
		}
	}

}
