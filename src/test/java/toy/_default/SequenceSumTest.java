package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SequenceSumTest {

	@Test(dataProvider = "maxData")
	public void maxTest(int[] in, int expected, String description) {
		int actual = SequenceSum.max(in);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] maxData() {
		return new Object[][] {
				{ new int[] { 1 }, 1, "len 1" },
				{ new int[] { 2, -8, 3, -2, 4, -10 }, 5,
						"mix of positive and negative integers" },
				{ new int[] { -3, -10, -5 }, -3,
						"all negative integers, returned value is first element" },
				{ new int[] { -3, -10, -5, -1, -11 }, -1,
						"all negative integers, returned value in the middle" } };
	}

	@Test(dataProvider = "containsData")
	public void containsTest(int[] a, int t, boolean expected,
			String description) {
		boolean actual = SequenceSum.contains(a, t);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] containsData() {
		return new Object[][] {
				{ new int[] { 23, 5, 4, 7, 2, 11 }, 20, true,
						"sequence at tail end" },
				{ new int[] { 1, 3, 5, 23, 2 }, 8, true, "sequence in middle" },
				{ new int[] { 1, 3, 5, 23, 2 }, 7, false,
						"no sequence - sequence broken by a number larger than target" },
				{ new int[] { 1, 3, 5, 23, 2 }, 9, true, "sequence at head" },
				{ new int[] { 1, 3, 5, 23, 2 }, 23, true,
						"sequence length is 1" } };
	}

}
