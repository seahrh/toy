package toy._default;

import java.util.Collections;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Numbers are randomly generated and stored into an (expanding) array. How
 * would you keep track of the median?
 * 
 */
public class MedianOfTwoHeaps {
	private static final Logger log = LoggerFactory.getLogger(MedianOfTwoHeaps.class);

	private MedianOfTwoHeaps() {
		// Not meant to be instantiated
	}

	/**
	 * Smaller half in max heap, bigger half in min heap.
	 * 
	 * Solution based on @url http://stackoverflow.com/a/10657732/519951
	 * 
	 * @param arr
	 * @return
	 */
	public static int median(int[] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		// Ensure array of length 2 or more will be
		// processed after this guard condition. Each heap will be at least size
		// 1.
		if (len == 1) {
			return arr[0];
		}
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(len);
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(len,
				Collections.reverseOrder());
		int min;
		int max;
		int minSize;
		int maxSize;
		// For the first two elements add smaller one to the maxHeap on the
		// left, and bigger one to the minHeap on the right.
		if (arr[0] > arr[1]) {
			minHeap.add(arr[0]);
			maxHeap.add(arr[1]);
		} else {
			minHeap.add(arr[1]);
			maxHeap.add(arr[0]);
		}
		for (int i = 2; i < len; i++) {
			min = minHeap.peek();
			max = maxHeap.peek();
			// if next item is smaller than maxHeap root add it to maxHeap,
			// else add it to minHeap
			if (arr[i] < max) {
				maxHeap.add(arr[i]);
			} else {
				minHeap.add(arr[i]);
			}
			// Balance the heaps (after this step heaps will be either balanced
			// or one of them will contain 1 more item).
			// if number of elements in one of the heaps is greater than the
			// other by more than 1, remove the root element from the one
			// containing more elements and add to the other one.
			minSize = minHeap.size();
			maxSize = maxHeap.size();
			if (minSize > maxSize + 1) {
				maxHeap.add(minHeap.remove());
			} else if (maxSize > minSize + 1) {
				minHeap.add(maxHeap.remove());
			}
		}
		minSize = minHeap.size();
		maxSize = maxHeap.size();
		min = minHeap.peek();
		max = maxHeap.peek();
		if (minSize > maxSize) {
			return min;
		}
		if (minSize < maxSize) {
			return max;
		}
		return (min + max) / 2;
	}

}
