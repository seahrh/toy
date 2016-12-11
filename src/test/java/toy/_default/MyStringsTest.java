package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyStringsTest {

	@Test(dataProvider = "isAnagramData")
	public void isAnagramTest(String s, String t, boolean expected,
			String description) {
		boolean actual = MyStrings.isAnagram(s, t);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] isAnagramData() {
		return new Object[][] { { "a", "a", true, "len 1 string" },
				{ "a", "b", false, "len 1 string" },
				{ "abcd", "bcda", true, "anagram with all unique chars" }, 
				{ "abbcccd", "cdbacbc", true, "anagram with duplicate chars" },
				{ "abbcccd", "cdbacbce", false, "unequal length strings" } };
	}

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
