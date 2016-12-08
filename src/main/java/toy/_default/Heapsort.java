package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heapsort {
	private static final Logger log = LoggerFactory.getLogger(Heapsort.class);

	private Heapsort() {
		// Not meant to be instantiated
	}

	public static void heapsort(int[] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		if (len == 1) {
			return;
		}
		// The following loop maintains the invariants that a[0:end] is a heap
		// and every element
		// beyond end is greater than everything before it (so a[end:len - 1] is
		// in sorted order.
		heapify(arr);
		int end = len - 1;
		while (end > 0) {
			swap(arr, 0, end);
			// the heap size is reduced by one
			end--;
			// the swap ruined the heap property, so restore it
			siftDown(arr, 0, end);
		}
	}

	/**
	 * Put array elements in max heap order, in-place
	 * 
	 * @param arr
	 */
	private static void heapify(int[] arr) {
		// Begin from the last parent node.
		// the last element in a 0-based array is at index count-1; find the
		// parent of that element
		int len = arr.length;
		int begin = indexOfParent(len - 1);
		while (begin >= 0) {
			// Sift down the node at index 'begin' to the proper place such
			// that all nodes below
			// the begin index are in heap order.
			siftDown(arr, begin, len - 1);
			// go to the next parent node
			begin--;
		}
		// after sifting down the root all nodes are in heap order
		log.debug("heapify: {}", toString(arr));
	}

	/**
	 * Repair the heap whose root element is at index 'begin', assuming the
	 * heaps rooted at its children are valid.
	 * 
	 * @param arr
	 * @param begin
	 *            begin index inclusive
	 * @param end
	 *            end index inclusive
	 */
	private static void siftDown(int[] arr, int begin, int end) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int len = arr.length;
		if (len == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		if (begin < 0 || begin >= len) {
			log.error("begin index out of bounds. [{}]", begin);
			throw new IllegalArgumentException();
		}
		if (end < 0 || end >= len) {
			log.error("end index out of bounds. [{}]", end);
			throw new IllegalArgumentException();
		}
		if (begin >= end) {
			return;
		}
		int root = begin;
		int child = indexOfLeftChild(root);
		// Keeps track of child to swap with
		int target = root;
		while (child <= end) {
			log.debug("arr[root]={}, arr[child]={}", arr[root], arr[child]);
			if (arr[target] < arr[child]) {
				target = child;
			}
			// If there is a right child and that child is greater
			child++;
			if (child <= end && arr[target] < arr[child]) {
				target = child;
			}
			// The root holds the largest element. Since we assume the heaps
			// rooted at the
			// children are valid, this means that we are done.
			if (target == root) {
				log.debug("siftDown: {}", toString(arr));
				return;
			}
			// repeat to continue sifting down the child now
			swap(arr, target, root);
			root = target;
			child = indexOfLeftChild(root);
		}
	}

	private static int indexOfParent(int i) {
		if (i < 1) {
			log.error("index must not be less than or equal to zero");
			throw new IllegalArgumentException();
		}
		return (i - 1) / 2;
	}

	private static int indexOfLeftChild(int i) {
		if (i < 0) {
			log.error("index must not be less than zero");
			throw new IllegalArgumentException();
		}
		return 2 * i + 1;
	}

	@SuppressWarnings("unused")
	private static int indexOfRightChild(int i) {
		return indexOfLeftChild(i) + 1;
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

	private static String toString(int[] in) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < in.length; i++) {
			sb.append(in[i]);
			if (i != in.length - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
