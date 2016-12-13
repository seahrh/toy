package toy._default;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class PairSumTest {
	@Test(dataProvider = "indexOfSumOperandsData")
	public void indexOfSumOperandsTest(int[] in, int sum, List<Integer> expected, String description) {
		List<Integer> actual = PairSum.indexOfSumOperands(in, sum);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] indexOfSumOperandsData() {
		return new Object[][] {
				{ new int[] { 1 }, 1, Lists.newArrayList(), "len 1 array" },
				{ new int[] { 2, 1 }, 3, Lists.newArrayList(0, 1), "len 2 array, 1 pair found" },
				{ new int[] { 2, 1 }, 4, Lists.newArrayList(), "len 2 array, no pairs" },
				{ new int[] { 6, 2, 3, 1, 8 }, 11, Lists.newArrayList(2, 4), "len 5 array, 1 pair found" },
				{ new int[] { 6, 2, 3, 1, 8 }, 20, Lists.newArrayList(), "len 5 array, no pairs" },
				{ new int[] { 6, 2, 3, 1, 8, 4, 11, 9 }, 12, Lists.newArrayList(2, 7, 3, 6, 4, 5), "len 8 array, 3 pairs found" } };
	}

}
