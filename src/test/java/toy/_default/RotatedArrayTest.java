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
	
	@Test(dataProvider = "binarySearchData")
	public void binarySearchTest(int[] in, int key, int expected, String description) {
		int actual = RotatedArray.binarySearch(in, key);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] binarySearchData() {
		return new Object[][] {
				{ new int[] { 1 }, 1, 0, "len 1 array, key exists" },
				{ new int[] { 1 }, 2, -1,
						"len 1 array, key does not exist" },
				{ new int[] { 2, 1 }, 2, 0, "len 2 array, key exists" },
				{ new int[] { 2, 1 }, 3, -1,
						"len 2 array, key does not exist" },
				{ new int[] { 3, 4, 5, 6, 7, 8, 1, 2 }, 8, 5, "len 8 array, both key and min in right partition, key is before min." },
				{ new int[] { 3, 4, 5, 6, 7, 8, 1, 2 }, 1, 6, "len 8 array, both key and min in right partition, key is min." },
				{ new int[] { 3, 4, 5, 6, 7, 8, 1, 2 }, 2, 7, "len 8 array, both key and min in right partition, key is after min." },
				{ new int[] { 3, 4, 5, 6, 7, 8, 1, 2 }, 10, -1, "len 8 array, key not found" },
				{ new int[] { 3, 4, 5, 6, 7, 8, 1, 2 }, 6, 3, "len 8 array, key is mid" },
				{ new int[] { 3, 4, 5, 6, 7, 8, 9, 1, 2 }, 8, 5, "len 9 array, both key and min in right partition, key is before min." },
				{ new int[] { 3, 4, 5, 6, 7, 8, 9, 1, 2 }, 1, 7, "len 9 array, both key and min in right partition, key is min." },
				{ new int[] { 3, 4, 5, 6, 7, 8, 9, 1, 2 }, 2, 8, "len 9 array, both key and min in right partition, key is after min." },
				{ new int[] { 3, 4, 5, 6, 7, 8, 9, 1, 2 }, 10, -1, "len 9 array, key not found" },
				{ new int[] { 3, 4, 5, 6, 7, 8, 9, 1, 2 }, 7, 4, "len 9 array, key is mid" },
				{ new int[] { 7, 8, 1, 2, 3, 4, 5, 6 }, 7, 0, "len 8 array, both key and min in left partition, key is before min." },
				{ new int[] { 7, 8, 1, 2, 3, 4, 5, 6 }, 1, 2, "len 8 array, both key and min in left partition, key is min." },
				{ new int[] { 8, 1, 2, 3, 4, 5, 6, 7 }, 2, 2, "len 8 array, both key and min in left partition, key is after min." },
				{ new int[] { 8, 9, 1, 2, 3, 4, 5, 6, 7 }, 9, 1, "len 9 array, both key and min in left partition, key is before min." },
				{ new int[] { 8, 9, 1, 2, 3, 4, 5, 6, 7 }, 1, 2, "len 9 array, both key and min in left partition, key is min." },
				{ new int[] { 8, 9, 1, 2, 3, 4, 5, 6, 7 }, 2, 3, "len 9 array, both key and min in left partition, key is after min." } };
	}


}
