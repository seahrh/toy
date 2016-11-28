package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quicksort {
	private static final Logger log = LoggerFactory.getLogger(Quicksort.class);

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
		// Base case: subarray of len 1 is already sorted
		// The end index decrements recursively
		// until it is less than begin index
		if (begin >= end) {
			return;
		}
		if (begin < 0 || begin >= len) {
			throw new IllegalArgumentException();
		}
		if (end < 0 || end >= len) {
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
		setMedianOfThreeAsLastElement(arr, begin, end);
		int pivot = arr[end];
		int pivotIndex = begin;
		for (int i = begin; i < end; i++) {
			if (arr[i] <= pivot) {
				swap(arr, i, pivotIndex);
				pivotIndex++;
			}
		}
		swap(arr, end, pivotIndex);
		log.info("pivot [{}], pivotIndex [{}]", pivot, pivotIndex);
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
		if (index1 == index2) {
			return;
		}
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	private static void setMedianOfThreeAsLastElement(int[] arr, int begin,
			int end) {
		if (arr == null) {
			throw new IllegalArgumentException();
		}
		if (begin == end) {
			throw new IllegalArgumentException();
		}
		// Do not modify the array if there are only 2 elements
		if (end - begin == 1) {
			return;
		}
		// Do not use (begin + end)/2 because integer overflow
		int mid = (end - begin) / 2 + begin;
		if (arr[begin] >= arr[mid] && arr[begin] <= arr[end]) {
			swap(arr, begin, end);
			return;
		}
		if (arr[begin] <= arr[mid] && arr[begin] >= arr[end]) {
			swap(arr, begin, end);
			return;
		}
		if (arr[mid] >= arr[begin] && arr[mid] <= arr[end]) {
			swap(arr, mid, end);
			return;
		}
		if (arr[mid] <= arr[begin] && arr[mid] >= arr[end]) {
			swap(arr, mid, end);
			return;
		}
		// Do nothing; last element is the pivot
	}

}
