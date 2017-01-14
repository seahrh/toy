package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BinarySearchTest {
	@Test(dataProvider = "nearestData")
	public void nearestTest(int[] in, int key, int expected, String description) {
		int actual = BinarySearch.nearest(in, key);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] nearestData() {
		final int[] evenSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		final int[] oddSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 17, 27 };
		return new Object[][] {
				{ new int[] { 1 }, 1, 0, "len 1 array, key exists" },
				{ new int[] { 1 }, 2, 0, "len 1 array, nearest value is 1" },
				{ new int[] { 1, 2 }, 2, 1, "len 2 array, key exists" },
				{ new int[] { 1, 2 }, 3, 1, "len 2 array, nearest value is 2" },
				{ evenSorted, 3, 2, "len 8 array, key exists" },
				{ evenSorted, 0, 0, "len 8 array, nearest value is 1" },
				{ evenSorted, 10, 7, "len 8 array, nearest value is 7" },
				{ oddSorted, 7, 6, "len 9 array, key exists" },
				{ oddSorted, 10, 6, "len 9 array, nearest value is 7" },
				{ oddSorted, 12, 6,
						"len 9 array, nearest value is 7 or 17 (both equidistant)" },
				{ oddSorted, 20, 7, "len 9 array, nearest value is 17" } };
	}

	@Test(dataProvider = "searchData")
	public void searchTest(int[] in, int key, int expected, String description) {
		int actual = BinarySearch.search(in, key);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] searchData() {
		final int[] evenSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		final int[] oddSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		return new Object[][] {
				{ new int[] { 1 }, 1, 0, "len 1 array, key exists" },
				{ new int[] { 1 }, 2, -1, "len 1 array, key does not exist" },
				{ new int[] { 1, 2 }, 2, 1, "len 2 array, key exists" },
				{ new int[] { 1, 2 }, 3, -1, "len 2 array, key does not exist" },
				{ evenSorted, 3, 2, "len 8 array, key exists" },
				{ evenSorted, 0, -1, "len 8 array, key does not exist" },
				{ evenSorted, 10, -1, "len 8 array, key does not exist" },
				{ oddSorted, 7, 6, "len 9 array, key exists" },
				{ oddSorted, 0, -1, "len 9 array, key does not exist" },
				{ oddSorted, 10, -1, "len 9 array, key does not exist" } };
	}

}
