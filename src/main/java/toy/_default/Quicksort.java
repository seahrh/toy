package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quicksort {
	private static final Logger log = LoggerFactory.getLogger(Quicksort.class);

	private Quicksort() {
		// Not meant to be instantiated
	}

	protected static void quicksort(int[] arr, int begin, int end,
			String partitionScheme) {
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
		int pivotIndex = begin;
		if (partitionScheme == null) {
			partitionScheme = "hoare";
		}
		if (partitionScheme.equals("lomuto")) {
			pivotIndex = partitionLomuto(arr, begin, end);
		} else {
			pivotIndex = partitionHoare(arr, begin, end);
		}
		quicksort(arr, begin, pivotIndex - 1, partitionScheme);
		quicksort(arr, pivotIndex + 1, end, partitionScheme);
	}

	public static void quicksort(int[] arr, int begin, int end) {
		quicksort(arr, begin, end, "hoare");
	}

	/**
	 * Lomuto partition scheme with median-of-three as pivot.
	 * 
	 * @param arr
	 * @param begin
	 * @param end
	 * @return
	 */
	private static int partitionLomuto(int[] arr, int begin, int end) {
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
		log.debug("pivot [{}], pivotIndex [{}]", pivot, pivotIndex);
		return pivotIndex;
	}

	/**
	 * Based on @url https://youtu.be/8hHWpuAPBHo
	 * 
	 * @param arr
	 * @param begin
	 * @param end
	 * @return
	 */
	private static int partitionHoare(int[] arr, int begin, int end) {
		setMedianOfThreeAsFirstElement(arr, begin, end);
		int pivot = arr[begin];
		int i = begin + 1;
		int j = end;
		while (true) {
			while (arr[i] < pivot && i <= j) {
				// Prevent ArrayOutOfBounds if i is incremented after the last element
				if (++i > end) {
					break;
				}
			}
			while (arr[j] > pivot && j >= i) {
				// Prevent ArrayOutOfBounds if j is decremented before the first element
				if (--j < begin) {
					break;
				}
			}
			if (i >= j) {
				break;
			}
			swap(arr, i++, j--);
		}
		int pivotIndex = i - 1;
		swap(arr, begin, pivotIndex);
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
		// Do not modify the array if there are only 2 elements
		if (end - begin == 1) {
			return;
		}
		int medianIndex = indexOfMedianOfThree(arr, begin, end);
		swap(arr, medianIndex, end);
	}

	private static void setMedianOfThreeAsFirstElement(int[] arr, int begin,
			int end) {
		// Do not modify the array if there are only 2 elements
		if (end - begin == 1) {
			return;
		}
		int medianIndex = indexOfMedianOfThree(arr, begin, end);
		swap(arr, medianIndex, begin);
	}

	private static int indexOfMedianOfThree(int[] arr, int begin, int end) {
		if (arr == null) {
			throw new IllegalArgumentException();
		}
		// There must be at least 3 elements between begin and end indices
		if (end - begin < 2) {
			throw new IllegalArgumentException();
		}
		// Do not use (begin + end)/2 because integer overflow
		int mid = (end - begin) / 2 + begin;
		if (arr[begin] >= arr[mid] && arr[begin] <= arr[end]) {
			return begin;
		}
		if (arr[begin] <= arr[mid] && arr[begin] >= arr[end]) {
			return begin;
		}
		if (arr[mid] >= arr[begin] && arr[mid] <= arr[end]) {
			return mid;
		}
		if (arr[mid] <= arr[begin] && arr[mid] >= arr[end]) {
			return mid;
		}
		return end;
	}

}
