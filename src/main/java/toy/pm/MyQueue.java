package toy.pm;

import java.util.Stack;

/**
 * Implement a queue using 2 stacks. The head of the queue is at the top of the
 * primary stack. The second stack is used as a temporary placeholder.
 * <p>
 * Dequeueing and peeking at the head of the queue take O(1) time.
 * <p>
 * Enqueueing, however, takes O(n) time. First, (n - 1) elements are popped from
 * the primary stack and pushed to the temp stack. This empties the primary
 * stack. The newest element is pushed to the primary stack and now it has a
 * length of 1. Finally, (n - 1) elements are popped from the temp stack and
 * pushed to the primary stack, refilling it.
 * <p>
 * The total length of both stacks will be n, where n is the length of the
 * queue. Thus, it takes O(n) space.
 * 
 * @param <T>
 */
public class MyQueue<T> {
	private Stack<T> stack = new Stack<>();
	private Stack<T> temp = new Stack<>();

	public MyQueue() {
		// no-op
	}

	public boolean isEmpty() {
		return stack.empty();
	}

	public T peek() {
		return stack.peek();
	}

	public T pop() {
		return stack.pop();
	}

	public void push(T t) {
		while (!stack.isEmpty()) {
			temp.push(stack.pop());
		}
		stack.push(t);
		while (!temp.empty()) {
			stack.push(temp.pop());
		}
	}

}
