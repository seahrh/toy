package toy._default;

public class Quicksort {

	private Quicksort() {
		// Not meant to be instantiated
	}

	public static void quicksort(int[] arr, int begin, int end) {
		if (arr == null) {
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			throw new IllegalArgumentException();
		}
		if (len == 1) {
			return;
		}
		if (begin < 0 || begin >= len) {
			throw new IllegalArgumentException();
		}
		if (end < 0 || end >= len) {
			throw new IllegalArgumentException();
		}
		if (begin > end) {
			throw new IllegalArgumentException();
		}
		int pivotIndex = partition(arr, begin, end);
		quicksort(arr, begin, pivotIndex - 1);
		quicksort(arr, pivotIndex + 1, end);
	}

	/**
	 * Lomuto partition scheme with median-of-three as pivot.
	 * 
	 * @param arr
	 * @param begin
	 * @param end
	 * @return
	 */
	private static int partition(int[] arr, int begin, int end) {
		// Do not use (begin + end)/2 because integer overflow
		int mid = (end - begin) / 2 + begin;
		int pivot = medianOfThree(new int[] {arr[begin], arr[mid], arr[end]});
		int pivotIndex = begin;
		for (int i = begin; i <= end; i++) {
			if (arr[i] <= pivot) {
				swap(arr, i, pivotIndex);
				pivotIndex++;
			}
		}
		return pivotIndex;
	}
	
	private static void swap(int[] arr, int index1, int index2) {
		if (arr == null) {
			throw new IllegalArgumentException();
		} 
		int len = arr.length;
		if (len == 0) {
			throw new IllegalArgumentException();
		}
		if (len == 1) {
			return;
		}
		if (index1 < 0 || index1 >= len) {
			throw new IllegalArgumentException();
		}
		if (index2 < 0 || index2 >= len) {
			throw new IllegalArgumentException();
		}
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	private static int medianOfThree(int[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException();
		}
		if (arr.length != 3) {
			throw new IllegalArgumentException();
		}
		if (arr[0] >= arr[1] && arr[0] <= arr[2]) {
			return arr[0];
		}
		if (arr[0] <= arr[1] && arr[0] >= arr[2]) {
			return arr[0];
		}
		if (arr[1] >= arr[0] && arr[1] <= arr[2]) {
			return arr[1];
		}
		if (arr[1] <= arr[0] && arr[1] >= arr[2]) {
			return arr[1];
		}
		if (arr[2] >= arr[0] && arr[2] <= arr[1]) {
			return arr[2];
		}
		return arr[2];
	}

}
