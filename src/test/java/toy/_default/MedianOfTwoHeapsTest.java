package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MedianOfTwoHeapsTest {

	@Test(dataProvider = "medianData")
	public void medianTest(int[] in, int expected,
			String description) {
		int actual = MedianOfTwoHeaps.median(in);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] medianData() {
		return new Object[][] {
				{ new int[] {1}, 1, "len 1 array" },
				{ new int[] {99, 1}, 50, "len 2 array" },
				{ new int[] {1, 2, 3, 4, 5, 6}, 3, "len 6 sorted array" }, 
				{ new int[] {6, 5, 4, 3, 2, 1}, 3, "len 6 reverse sorted array" },
				{ new int[] {6, 1, 7, 4, 2, 5, 3}, 4, "len 7 array" },
				{ new int[] {6, 1, 7, 4, 2, 8, 5, 3}, 4, "len 8 array" },
				{ new int[] {2, 2, 2, 2}, 2, "duplicates len 4" },
				{ new int[] {2, 2, 2, 2, 2}, 2, "duplicates len 5" },
				{ new int[] {1, 2, 1, 2, 1}, 1, "duplicates 3x1 2x2" }, 
				{ new int[] {3, 1, 2, 2, 1, 3}, 2, "duplicates 1x2 2x2 3x2" } };
	}

}
