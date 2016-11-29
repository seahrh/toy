package toy._default;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class MergesortTest {
	private static final Logger log = LoggerFactory.getLogger(MergesortTest.class);

	@Test(dataProvider = "data")
	public void test(int[] in, int[] expected, String description) {
		Mergesort.mergesort(in);
		log.debug("\n\n{}\n\n", debug(in));
		assertArrayEquals(expected, in);
	}

	@DataProvider
	public Object[][] data() {
		final int[] evenSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		final int[] oddSorted = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
		Collections.shuffle(list);
		int[] even1 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] even2 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] even3 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] even4 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] even5 = Ints.toArray(list);
		list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Collections.shuffle(list);
		int[] odd1 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] odd2 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] odd3 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] odd4 = Ints.toArray(list);
		Collections.shuffle(list);
		int[] odd5 = Ints.toArray(list);
		return new Object[][] { { new int[] { 1 }, new int[] { 1 }, "len 1 array" },
				{ new int[] { 2, 1 }, new int[] { 1, 2 }, "len 2 unsorted array" },
				{ new int[] { 20, 1 }, new int[] { 1, 20 }, "len 2 unsorted non-consecutive array" },
				{ new int[] { 1, 2 }, new int[] { 1, 2 }, "len 2 sorted array" },
				{ new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }, evenSorted, "len 8 sorted array" },
				{ new int[] { 8, 7, 6, 5, 4, 3, 2, 1 }, evenSorted, "len 8 reverse sorted array" },
				{ new int[] { 7, 4, 1, 5, 8, 2, 3, 6 }, evenSorted, "len 8 unsorted array" },
				{ new int[] { 7, 4, 1, 9, 5, 8, 2, 3, 6 }, oddSorted, "odd len 9 unsorted array" },
				{ new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }, "len 8 all equal elements array" },
				{ new int[] { 4, 3, 2, 1, 1, 3, 2, 4 }, new int[] { 1, 1, 2, 2, 3, 3, 4, 4 }, "len 8 array with duplicate elements" },
				{ even1, evenSorted, "len 8 array even len random" }, 
				{ even2, evenSorted, "len 8 array even len random" }, 
				{ even3, evenSorted, "len 8 array even len random" }, 
				{ even4, evenSorted, "len 8 array even len random" }, 
				{ even5, evenSorted, "len 8 array even len random" },
				{ odd1, oddSorted, "len 9 array odd len random" },
				{ odd2, oddSorted, "len 9 array odd len random" }, 
				{ odd3, oddSorted, "len 9 array odd len random" },
				{ odd4, oddSorted, "len 9 array odd len random" },
				{ odd5, oddSorted, "len 9 array odd len random" } };
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
