package toy._default;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TowersOfHanoi {
	private static final Logger log = LoggerFactory.getLogger(TowersOfHanoi.class);
	private static int n = 4;
	private static Stack<Integer> a = new Stack<>();
	private static Stack<Integer> b = new Stack<>();
	private static Stack<Integer> c = new Stack<>();

	static {
		int i = n;
		while (i > 0) {
			a.push(i);
			i--;
		}
	}

	private TowersOfHanoi() {
		// Not meant to be instantiated
	}

	public static void main(String[] args) {
		move(n, a, c, b);
	}

	/**
	 * Recursive solution to Towers of Hanoi problem.
	 * This takes O(2^n) time.
	 * 
	 * @param m
	 *            number of disks to move
	 * @param from
	 * @param to
	 * @param buf
	 */
	private static void move(int m, Stack<Integer> from, Stack<Integer> to,
			Stack<Integer> buf) {
		// Base case: no disk to move
		if (m == 0) {
			return;
		}
		move(m - 1, from, buf, to);
		to.push(from.pop());
		log.info("m={}\na={}\nb={}\nc={}", m, a, b, c);
		move(m - 1, buf, to, from);
	}

}
