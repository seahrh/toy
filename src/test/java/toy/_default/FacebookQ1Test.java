package toy._default;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FacebookQ1Test {

	@Test(dataProvider = "data")
	public void test(int[] a, int t, boolean expected) {
		boolean actual = FacebookQ1.containsSequenceSum(a, t);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] data() {
		return new Object[][] { { new int[] { 23, 5, 4, 7, 2, 11 }, 20, true },
				{ new int[] { 1, 3, 5, 23, 2 }, 8, true },
				{ new int[] { 1, 3, 5, 23, 2 }, 7, false } };
	}

}
