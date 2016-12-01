package toy.pm;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EvenNumberFirstTest {
	private static final Logger log = LoggerFactory.getLogger(EvenNumberFirstTest.class);

	@Test(dataProvider = "data")
	public void test(int[] in, int[] expected, String description) {
		EvenNumberFirst.partition(in);
		log.debug("\n\n{}\n\n", debug(in));
		assertArrayEquals(expected, in);
	}

	@DataProvider
	public Object[][] data() {
		return new Object[][] {
				{ new int[] { 1 }, new int[] { 1 }, "len 1 array odd number" },
				{ new int[] { 2 }, new int[] { 2 }, "len 1 array even number" },
				{ new int[] { 2, 1 }, new int[] { 2, 1 }, "len 2 array sorted" },
				{ new int[] { 1, 2 }, new int[] { 2, 1 }, "len 2 array unsorted" },
				{ new int[] { 2, 4, 6, 4, 7, 1, 3, 5 }, new int[] { 2, 4, 6, 4, 7, 1, 3, 5 }, "even len 8 array sorted" },
				{ new int[] { 2, 4, 7, 6, 1, 3, 5, 4 }, new int[] { 2, 4, 4, 6, 1, 3, 5, 7 }, "even len 8 array unsorted (example given in the question)" },
				{ new int[] { 10, 2, 4, 6, 4, 7, 1, 3, 5 }, new int[] { 10, 2, 4, 6, 4, 7, 1, 3, 5 }, "odd len 9 array sorted" },
				{ new int[] { 2, 4, 7, 6, 1, 3, 5, 4, 10 }, new int[] { 2, 4, 10, 6, 4, 3, 5, 1, 7 }, "odd len 9 array unsorted" },
				{ new int[] { 1, 1, 1 }, new int[] { 1, 1, 1 }, "all odd values" },
				{ new int[] { 2, 2, 2 }, new int[] { 2, 2, 2 }, "all even values" },
				{ new int[] { 2, -4, -7, 6, 1, 3, 5, -4 }, new int[] { 2, -4, -4, 6, 1, 3, 5, -7 }, "negative numbers" } };
	}
	
	private static String debug(int[] in) {
		StringBuilder sb = new StringBuilder("actual: ");
		for (int i = 0; i < in.length; i++) {
			sb.append(in[i]);
			if (i != in.length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}
