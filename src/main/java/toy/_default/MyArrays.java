package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyArrays {
	private static final Logger log = LoggerFactory.getLogger(MyArrays.class);

	private MyArrays() {
		// Not meant to be instantiated
	}

	/**
	 * Given a matrix in which each row and each column is sorted, find an
	 * element in it. [CTCI Q9.6]
	 * <p>
	 * This algorithm works by elimination. Every move to the left (--col)
	 * eliminates all the elements below the current cell in that column.
	 * Likewise, every move down eliminates all the elements to the left of the
	 * cell in that row.
	 * <p>
	 * This takes O(m+n) time and O(1) space.
	 * 
	 * @param matrix
	 * @param key
	 * @return
	 */
	public static int[] searchSortedMatrix(int[][] matrix, int key) {
		if (matrix == null) {
			log.error("matrix must not be null");
			throw new IllegalArgumentException();
		}
		int rows = matrix.length;
		if (rows == 0) {
			log.error("matrix must not be empty");
			throw new IllegalArgumentException();
		}
		int cols = matrix[0].length;
		// Start searching from top right corner
		int i = 0;
		int j = cols - 1;
		while (i < rows && j > -1) {
			if (matrix[i][j] == key) {
				return new int[] { i, j };
			}
			if (key < matrix[i][j]) {
				j--;
			} else {
				i++;
			}
		}
		return new int[] { -1, -1 };
	}

	/**
	 * Q1.6
	 * <p>
	 * Rotate by +90: Transpose, then reverse each row.
	 * <p>
	 * This takes O(n^2) time and O(1) space
	 * 
	 * @param arr
	 */
	public static void rotate90DegreesRight(int[][] arr) {
		transpose(arr);
		reverseRows(arr);
	}

	/**
	 * Q1.6
	 * <p>
	 * Rotate by -90: Transpose, then reverse each column.
	 * <p>
	 * This takes O(n^2) time and O(1) space
	 * 
	 * @param arr
	 */
	public static void rotate90DegreesLeft(int[][] arr) {
		transpose(arr);
		reverseColumns(arr);
	}

	/**
	 * Q1.6
	 * <p>
	 * Reverse each row and then reverse each column.
	 * <p>
	 * This takes O(n^2) time and O(1) space
	 * 
	 * @param arr
	 */
	public static void rotate180Degrees(int[][] arr) {
		reverseRows(arr);
		reverseColumns(arr);
	}

	public static void reverseColumns(int[][] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int rows = arr.length;
		if (rows == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		int cols = arr[0].length;
		int i;
		int j;
		for (int k = 0; k < cols; k++) {
			i = 0;
			j = rows - 1;
			while (i < j) {
				swap(arr, i, k, j, k);
				i++;
				j--;
			}
		}
	}

	public static void reverseRows(int[][] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int rows = arr.length;
		if (rows == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < rows; i++) {
			reverse(arr[i]);
		}
	}

	public static void reverse(int[] arr) {
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
		int i = 0;
		int j = len - 1;
		while (i < j) {
			swap(arr, i, j);
			i++;
			j--;
		}
	}

	public static void transpose(int[][] arr) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int rows = arr.length;
		if (rows == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		int cols = arr[0].length;
		if (rows != cols) {
			log.error("2d array must be a square matrix");
			throw new IllegalArgumentException();
		}
		// Swap the upper triangle with lower triangle
		for (int i = 0; i < rows; i++) {
			// Start from the element in the upper triangle
			for (int j = i + 1; j < cols; j++) {
				swap(arr, i, j, j, i);
			}
		}
	}

	public static void swap(int[][] arr, int i1, int j1, int i2, int j2) {
		if (arr == null) {
			log.error("array must not be null");
			throw new IllegalArgumentException();
		}
		int rows = arr.length;
		if (rows == 0) {
			log.error("array must not be empty");
			throw new IllegalArgumentException();
		}
		int cols = arr[0].length;
		if (rows == 1 && rows == cols) {
			return;
		}
		if (i1 < 0 || i1 >= rows) {
			log.error("array index out of bounds. i1 [{}]", i1);
			throw new IllegalArgumentException();
		}
		if (i2 < 0 || i2 >= rows) {
			log.error("array index out of bounds. i2 [{}]", i2);
			throw new IllegalArgumentException();
		}
		if (j1 < 0 || j1 >= cols) {
			log.error("array index out of bounds. j1 [{}]", j1);
			throw new IllegalArgumentException();
		}
		if (j2 < 0 || j2 >= cols) {
			log.error("array index out of bounds. j2 [{}]", j2);
			throw new IllegalArgumentException();
		}
		if (i1 == i2 && j1 == j2) {
			return;
		}
		int temp = arr[i1][j1];
		arr[i1][j1] = arr[i2][j2];
		arr[i2][j2] = temp;
	}

	public static void swap(int[] arr, int i1, int i2) {
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
		if (i1 < 0 || i1 >= len) {
			log.error("array index out of bounds. i1 [{}]", i1);
			throw new IllegalArgumentException();
		}
		if (i2 < 0 || i2 >= len) {
			log.error("array index out of bounds. i2 [{}]", i2);
			throw new IllegalArgumentException();
		}
		if (i1 == i2) {
			return;
		}
		int temp = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = temp;
	}
}