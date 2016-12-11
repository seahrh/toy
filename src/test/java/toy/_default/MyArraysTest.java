package toy._default;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyArraysTest {
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
