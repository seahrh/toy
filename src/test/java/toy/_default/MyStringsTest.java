package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyStringsTest {

	@Test(dataProvider = "removeDuplicatesData")
	public void removeDuplicatesTest(String in, String expected,
			String description) {
		String actual = MyStrings.removeDuplicates(in);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] removeDuplicatesData() {
		return new Object[][] { { "a", "a", "len 1 string" },
				{ "aa", "a", "len 2 string with duplicates" },
				{ "aaaaa", "a", "all duplicates" },
				{ "abcde", "abcde", "no duplicates" },
				{ "abbccd", "abcd", "contiguous duplicates" },
				{ "abbcddeff", "abcdef", "non contiguous duplicates" },
				{ "abababa", "ab", "non contiguous duplicates" } };
	}

}
