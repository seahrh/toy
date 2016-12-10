package toy._default;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Sets;

public class StringPermutationTest {

	@Test(dataProvider = "permutateData")
	public void permutateTest(String in, Set<String> expected,
			String description) {
		Set<String> actual = StringPermutation.permutate(in);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] permutateData() {
		return new Object[][] {
				{ "a", Sets.newHashSet("a"), "len 1 string" },
				{ "ab", Sets.newHashSet("ba", "ab"), "len 2 string 4 perms" },
				{
						"abc",
						Sets.newHashSet("cab", "acb", "abc", "cba", "bca",
								"bac"), "len 3 string 6 perms" },
				{
						"abcd",
						Sets.newHashSet("dcab", "cdab", "cadb", "cabd", "dacb",
								"adcb", "acdb", "acbd", "dabc", "adbc", "abdc",
								"abcd", "dcba", "cdba", "cbda", "cbad", "dbca",
								"bdca", "bcda", "bcad", "dbac", "bdac", "badc",
								"bacd"), "len 4 string 24 perms" } };
	}

}
