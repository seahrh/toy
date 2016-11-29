package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BinarySearchTest {
	@Test(dataProvider = "data")
	public void test(int[] in, int key, int expected, String description) {
		int actual = BinarySearch.binarySearch(in, key);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] data() {
		final int[] evenSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		final int[] oddSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		return new Object[][] {
				{ new int[] { 1 }, 1, 0, "len 1 array, key exists" },
				{ new int[] { 1 }, 2, -1,
						"len 1 array, key does not exist" },
				{ new int[] { 1, 2 }, 2, 1, "len 2 array, key exists" },
				{ new int[] { 1, 2 }, 3, -1,
						"len 2 array, key does not exist" },
				{ evenSorted, 3, 2, "len 8 array, key exists" },
				{ evenSorted, 0, -1,
						"len 8 array, key does not exist" },
				{ evenSorted, 10, -1,
						"len 8 array, key does not exist" },
				{ oddSorted, 7, 6, "len 9 array, key exists" },
				{ oddSorted, 0, -1,
						"len 9 array, key does not exist" },
				{ oddSorted, 10, -1,
						"len 9 array, key does not exist" } };
	}

}
