package toy.pm;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * All test cases test the push operation ("enqueue").
 *
 */
public class MyQueueTest {
	@Test(dataProvider = "isEmptyData")
	public void isEmptyTest(int[] in, boolean expected, String description) {
		MyQueue<Integer> q = new MyQueue<>();
		for (Integer i : in) {
			q.push(i);
		}
		assertEquals(q.isEmpty(), expected);
	}

	@DataProvider
	public Object[][] isEmptyData() {
		return new Object[][] { { new int[] {}, true, "empty queue" },
				{ new int[] { 1 }, false, "non empty queue" }, };
	}
	
	@Test(dataProvider = "peekData")
	public void peekTest(int[] in, int expected, String description) {
		MyQueue<Integer> q = new MyQueue<>();
		for (Integer i : in) {
			q.push(i);
		}
		// Peek should not remove the element
		int actual = q.peek();
		assertEquals(actual, expected);
		actual = q.pop();
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] peekData() {
		return new Object[][] { { new int[] {1}, 1, "queue len 1" },
				{ new int[] { 1,2 }, 1, "queue len 2" }, };
	}
	
	@Test(dataProvider = "popData")
	public void popTest(int[] in, String description) {
		MyQueue<Integer> q = new MyQueue<>();
		for (Integer i : in) {
			q.push(i);
		}
		int i = 0;
		while (!q.isEmpty()) {
			assertEquals((int) q.pop(), in[i]);
			i++;
		}
	}

	@DataProvider
	public Object[][] popData() {
		return new Object[][] { { new int[] {1}, "queue len 1" },
				{ new int[] { 1,2,3,4,5,6,7,8 }, "queue len 8" }, 
				{ new int[] { 1,2,3,4,5,6,7,8,9 }, "queue len 9" } };
	}

}
