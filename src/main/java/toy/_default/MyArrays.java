package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyArrays {
	private static final Logger log = LoggerFactory.getLogger(MyArrays.class);

	private MyArrays() {
		// Not meant to be instantiated
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