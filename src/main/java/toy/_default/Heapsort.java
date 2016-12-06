package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heapsort {
	private static final Logger log = LoggerFactory.getLogger(Quicksort.class);

	private Heapsort() {
		// Not meant to be instantiated
	}

	/**
	 * Put array elements in heap order, in-place
	 * 
	 * @param arr
	 */
	private static void heapify(int[] arr) {
		// Begin from the last parent node.
		// the last element in a 0-based array is at index count-1; find the
		// parent of that element
		int begin = indexOfParent(arr.length - 1);
		while (begin >= 0) {
			// Sift down the node at index 'begin' to the proper place such
			// that all nodes below
			// the begin index are in heap order.
			siftDown(arr, begin);
			// go to the next parent node
			begin--;
		}
		// after sifting down the root all nodes are in heap order
	}

	/**
	 * Repair the heap whose root element is at index 'begin', assuming the
	 * heaps rooted at its children are valid.
	 * 
	 * @param arr
	 * @param begin
	 */
	private static void siftDown(int[] arr, int begin) {
		int len = arr.length;
		int root = begin;
		int leftChild = indexOfLeftChild(root);
		int rightChild;
		while (leftChild < len) {
			rightChild = leftChild + 1;
			if (arr[root] < arr[leftChild]) {
				swap(arr, root, leftChild);
				root = leftChild;
				leftChild = indexOfLeftChild(root);
				// repeat to continue sifting down the child now
				continue;
			}
			if (rightChild < len && arr[root] < arr[rightChild]) {
				swap(arr, root, rightChild);
				root = rightChild;
				leftChild = indexOfLeftChild(root);
				continue;
			}
			// The root holds the largest element. Since we assume the heaps
			// rooted at the
			// children are valid, this means that we are done.
			return;
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

	private static int indexOfRightChild(int i) {
		if (i < 0) {
			log.error("index must not be less than zero");
			throw new IllegalArgumentException();
		}
		return 2 * i + 2;
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

}
