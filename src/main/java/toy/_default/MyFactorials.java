package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFactorials {
	private static final Logger log = LoggerFactory.getLogger(MyFactorials.class);

	private MyFactorials() {
		// Not meant to be instantiated
	}

	/**
	 * Computes the number of trailing zeros in n factorial
	 * <p>
	 * CTCI Q19.3
	 * 
	 * @param n
	 * @return
	 */
	public static int countTrailingZeroes(int n) {
		if (n < 0) {
			log.error("Factorial is not defined for negative numbers");
			throw new IllegalArgumentException();
		}
		int count = 0;
		// Trailing zeros are contributed by pairs of 5 and 2, because 5*2 = 10.
		// To count the number of pairs, we just have to count the number of
		// multiples of 5. Note that while 5 contributes to one multiple of 10,
		// 25 contributes two (because 25 = 5*5).
		for (int i = 5; n / i > 0; i *= 5) {
			count += n / i;
		}
		return count;
	}

}
