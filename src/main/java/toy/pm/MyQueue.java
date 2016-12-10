package toy.pm;

import java.util.Stack;

/**
 * Implement a queue using 2 stacks. The head of the queue is at the top of the
 * out stack. The in stack is used to hold new inserts.
 * <p>
 * Enqueueing takes O(1) time.
 * <p>
 * Peek and dequeue take amortized constant time in the average case, and O(n)
 * time in the worst case. When the out stack is empty, refill the out stack by
 * popping the elements from the in stack and pushing them to the out stack.
 * <p>
 * The total length of both stacks will be n, where n is the length of the
 * queue. Thus, it takes O(n) space.
 * 
 * @param <T>
 */
public class MyQueue<T> {
	private Stack<T> out = new Stack<>();
	private Stack<T> in = new Stack<>();

	public MyQueue() {
		// no-op
	}

	public int size() {
		return in.size() + out.size();
	}

	public boolean isEmpty() {
		return in.isEmpty() && out.isEmpty();
	}

	public T peek() {
		if (out.isEmpty()) {
			while (!in.isEmpty()) {
				out.push(in.pop());
			}
		}
		return out.peek();
	}

	public T pop() {
		peek();
		return out.pop();
	}

	public void push(T t) {
		in.push(t);
	}

}
