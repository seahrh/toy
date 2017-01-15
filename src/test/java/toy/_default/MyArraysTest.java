package toy._default;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyArraysTest {
	@Test(dataProvider = "searchSortedMatrixData")
	public void searchSortedMatrixTest(int[][] matrix, int key, int[] expected,
			String description) {
		int[] actual = MyArrays.searchSortedMatrix(matrix, key);
		assertArrayEquals(actual, expected);
	}

	@DataProvider
	public Object[][] searchSortedMatrixData() {
		final int[][] sq2x2 = new int[][] { { 1, 2 }, { 3, 4 } };
		final int[][] rect3x4 = new int[][] { { 1, 2, 3, 4 },
				{ 11, 12, 13, 14 }, { 21, 22, 23, 24 } };
		return new Object[][] {
				{ new int[][] { { 1 } }, 1, new int[] { 0, 0 },
						"1x1 matrix, key found" },
				{ new int[][] { { 1 } }, 2, new int[] { -1, -1 },
						"1x1 matrix, key not found" },
				{ sq2x2, 3, new int[] { 1, 0 }, "2x2 matrix, key found" },
				{ sq2x2, 5, new int[] { -1, -1 }, "2x2 matrix, key not found" },
				{ rect3x4, 12, new int[] { 1, 1 }, "3x4 matrix, key found" },
				{ rect3x4, 10, new int[] { -1, -1 },
						"3x4 matrix, key not found" } };
	}

	@Test(dataProvider = "rotate180DegreesData")
	public void rotate180DegreesTest(int[][] in, int[][] expected,
			String description) {
		MyArrays.rotate180Degrees(in);
		assertArrayEquals(in, expected);
	}

	@DataProvider
	public Object[][] rotate180DegreesData() {
		return new Object[][] {
				{ new int[][] { { 1 } }, new int[][] { { 1 } }, "1x1 array" },
				{ new int[][] { { 1, 2 }, { 3, 4 } },
						new int[][] { { 4, 3 }, { 2, 1 } }, "2x2 array" },
				{ new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
						new int[][] { { 9, 8, 7 }, { 6, 5, 4 }, { 3, 2, 1 } },
						"3x3 array" },
				{
						new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
								{ 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
						new int[][] { { 16, 15, 14, 13 }, { 12, 11, 10, 9 },
								{ 8, 7, 6, 5 }, { 4, 3, 2, 1 } }, "4x4 array" } };
	}

	@Test(dataProvider = "rotate90DegreesLeftData")
	public void rotate90DegreesLeftTest(int[][] in, int[][] expected,
			String description) {
		MyArrays.rotate90DegreesLeft(in);
		assertArrayEquals(in, expected);
	}

	@DataProvider
	public Object[][] rotate90DegreesLeftData() {
		return new Object[][] {
				{ new int[][] { { 1 } }, new int[][] { { 1 } }, "1x1 array" },
				{ new int[][] { { 1, 2 }, { 3, 4 } },
						new int[][] { { 2, 4 }, { 1, 3 } }, "2x2 array" },
				{ new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
						new int[][] { { 3, 6, 9 }, { 2, 5, 8 }, { 1, 4, 7 } },
						"3x3 array" },
				{
						new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
								{ 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
						new int[][] { { 4, 8, 12, 16 }, { 3, 7, 11, 15 },
								{ 2, 6, 10, 14 }, { 1, 5, 9, 13 } },
						"4x4 array" } };
	}

	@Test(dataProvider = "rotate90DegreesRightData")
	public void rotate90DegreesRightTest(int[][] in, int[][] expected,
			String description) {
		MyArrays.rotate90DegreesRight(in);
		assertArrayEquals(in, expected);
	}

	@DataProvider
	public Object[][] rotate90DegreesRightData() {
		return new Object[][] {
				{ new int[][] { { 1 } }, new int[][] { { 1 } }, "1x1 array" },
				{ new int[][] { { 1, 2 }, { 3, 4 } },
						new int[][] { { 3, 1 }, { 4, 2 } }, "2x2 array" },
				{ new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
						new int[][] { { 7, 4, 1 }, { 8, 5, 2 }, { 9, 6, 3 } },
						"3x3 array" },
				{
						new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
								{ 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
						new int[][] { { 13, 9, 5, 1 }, { 14, 10, 6, 2 },
								{ 15, 11, 7, 3 }, { 16, 12, 8, 4 } },
						"4x4 array" } };
	}

	@Test(dataProvider = "transposeData")
	public void transposeTest(int[][] in, int[][] expected, String description) {
		MyArrays.transpose(in);
		assertArrayEquals(in, expected);
	}

	@DataProvider
	public Object[][] transposeData() {
		return new Object[][] {
				{ new int[][] { { 1 } }, new int[][] { { 1 } }, "1x1 array" },
				{ new int[][] { { 1, 2 }, { 3, 4 } },
						new int[][] { { 1, 3 }, { 2, 4 } }, "2x2 array" },
				{ new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
						new int[][] { { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 } },
						"3x3 array" },
				{
						new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
								{ 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
						new int[][] { { 1, 5, 9, 13 }, { 2, 6, 10, 14 },
								{ 3, 7, 11, 15 }, { 4, 8, 12, 16 } },
						"4x4 array" } };
	}

}
