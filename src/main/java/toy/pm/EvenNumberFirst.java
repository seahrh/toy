package toy.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EvenNumberFirst {
	private static final Logger log = LoggerFactory.getLogger(EvenNumberFirst.class);

	private EvenNumberFirst() {
		// Not meant to be instantiated
	}

	/**
	 * Shuffle an integer array into two partitions: the first partition
	 * contains all even numbers and the second partition contains all odd
	 * numbers.
	 * <p>
	 * Have two pointers start at the head and tail of the array, and walk the
	 * two pointers towards the middle of the array. Increment the head pointer
	 * until an odd number is found. Decrement the tail pointer until an even
	 * number is found. Swap the elements in the two pointers. Stop the loop
	 * when the two pointers meet. Based on quicksort's Hoare partitioning
	 * scheme.
	 * <p>
	 * This takes O(n) time as n comparisons are made. The shuffling occurs
	 * in-place by swapping iteratively (no recursion), therefore space is O(1).
	 * 
	 * @param arr
	 *            integer array - must not be null or empty
	 */
	public static void partition(int[] arr) {
		if (arr == null) {
			log.error("Array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("Array must not be empty");
			throw new IllegalArgumentException();
		}
		if (len == 1) {
			return;
		}
		int i = 0;
		int j = len - 1;
		while (true) {
			while (arr[i] % 2 == 0 && i < j) {
				i++;
			}
			while (arr[j] % 2 == 1 && j > i) {
				j--;
			}
			if (i >= j) {
				break;
			}
			swap(arr, i, j);
			i++;
			j--;
		}
	}

	/**
	 * Swap the array elements at the specified indices.
	 * 
	 * @param arr
	 *            integer array - must not be null or empty
	 * @param thisIndex
	 *            index of the array element to be swapped
	 * @param thatIndex
	 *            index of the array element to be swapped
	 */
	private static void swap(int[] arr, int thisIndex, int thatIndex) {
		if (arr == null) {
			log.error("Array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("Array must not be empty");
			throw new IllegalArgumentException();
		}
		if (len == 1) {
			return;
		}
		if (thisIndex < 0 || thisIndex >= len) {
			log.error("Index [{}] out of bounds", thisIndex);
			throw new IllegalArgumentException();
		}
		if (thatIndex < 0 || thatIndex >= len) {
			log.error("Index [{}] out of bounds", thatIndex);
			throw new IllegalArgumentException();
		}
		if (thisIndex == thatIndex) {
			return;
		}
		int temp = arr[thisIndex];
		arr[thisIndex] = arr[thatIndex];
		arr[thatIndex] = temp;
	}

}
