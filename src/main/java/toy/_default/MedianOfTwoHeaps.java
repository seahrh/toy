package toy._default;

import java.util.Collections;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Numbers are randomly generated and stored into an (expanding) array. How would you keep track of the median?
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
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(len);
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(len,
				Collections.reverseOrder());
		int min = 0;
		int max = 0;
		int minSize;
		int maxSize;
		for (int i = 0; i < len; i++) {
			minSize = minHeap.size();
			maxSize = maxHeap.size();
			if (minSize > maxSize) {
				maxHeap.add(arr[i]);
			} else {
				minHeap.add(arr[i]);
			}
			if (minHeap.isEmpty() || maxHeap.isEmpty()) {
				continue;
			}
			min = minHeap.peek();
			max = maxHeap.peek();
			if (max > min) {
				minHeap.remove();
				maxHeap.remove();
				minHeap.add(max);
				maxHeap.add(min);
			}
		}
		minSize = minHeap.size();
		maxSize = maxHeap.size();
		if (minSize != 0) {
			min = minHeap.peek();
		}
		if (maxSize != 0) {
			max = maxHeap.peek();
		}
		if (maxSize == 0 || minSize > maxSize) {
			return min;
		}
		if (minSize == 0 || minSize < maxSize) {
			return max;
		}
		return (min + max) / 2;
	}

}
