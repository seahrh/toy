package toy.pm;

/**
 * Order of method calls are enforced using semaphores. Threads access
 * semaphores sequentially through synchronised methods (no interleaving is
 * possible).
 * <p>
 * If the methods are not called in order, exception is thrown and the
 * calling thread releases its lock on Foo instance.
 * 
 */
public class Foo {

	/**
	 * Semaphore that controls access to second()
	 */
	private boolean isSecondLocked = true;
	/**
	 * Semaphore that controls access to third()
	 */
	private boolean isThirdLocked = true;

	public Foo() {
		// stub
	}

	public synchronized void first() {
		isSecondLocked = false;
	}

	public synchronized void second() {
		if (isSecondLocked) {
			throw new IllegalStateException(
					"first() must be called before second()");
		}
		isThirdLocked = false;
	}

	public synchronized void third() {
		if (isThirdLocked) {
			throw new IllegalStateException(
					"second() must be called before third()");
		}
	}

}
