package toy.pm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EvenNumberFirst {
	private static final Logger log = LoggerFactory.getLogger(EvenNumberFirst.class);

	private EvenNumberFirst() {
		// Not meant to be instantiated
	}
	
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
