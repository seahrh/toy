package toy._default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fibonacci {
	private static final Logger log = LoggerFactory.getLogger(Fibonacci.class);

	private Fibonacci() {
		// Not meant to be instantiated
	}

	/**
	 * Generate the nth Fibonacci number. Assumes the fibonacci sequence starts
	 * from 0, like the following: 0, 1, 1, 2, 3, 5, 8, ...
	 * 
	 * @param n
	 * @return
	 */
	public static int fib(int n) {
		if (n < 1) {
			log.error("n must not be less than or equal to zero");
			throw new IllegalArgumentException();
		}
		// Seed values: the first two fib numbers
		if (n == 1) {
			return 0;
		}
		if (n == 2) {
			return 1;
		}
		return fib(n - 1) + fib(n - 2);
	}

}
