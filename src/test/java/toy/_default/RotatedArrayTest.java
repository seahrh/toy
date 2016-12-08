package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RotatedArrayTest {
	@Test(dataProvider = "minData")
	public void minTest(int[] in, int expected, String description) {
		int actual = RotatedArray.min(in);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] minData() {
		return new Object[][] {
				{ new int[] { 1 }, 0, "len 1 array" },
				{ new int[] { 2, 1 }, 1, "len 2 array" },
				{ new int[] { 3, 4, 5, 6, 7, 8, 1, 2 }, 6, "len 8 array, min exists in right partition" },
				{ new int[] { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, 6, "len 9 array, min exists in right partition" },
				{ new int[] { 8, 1, 2, 3, 4, 5, 6, 7 }, 1, "len 8 array, min exists in left partition" },
				{ new int[] { 8, 9, 1, 2, 3, 4, 5, 6, 7 }, 2, "len 9 array, min exists in left partition" },
				{ new int[] { 2, 3, 4, 5, 6, 7, 8, 1 }, 7, "min is last element" } };
	}

}
