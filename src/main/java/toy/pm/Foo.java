package toy.pm;

import java.util.concurrent.Semaphore;

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

	private Semaphore firstLock = new Semaphore(0);
	private Semaphore secondLock = new Semaphore(0);
	private Semaphore thirdLock = new Semaphore(1);

	public Foo() {
		// stub
	}

	public synchronized void first() throws InterruptedException {
		thirdLock.acquire();
		firstLock.release();
	}

	public synchronized void second() throws InterruptedException {
		firstLock.acquire();
		secondLock.release();
	}

	public synchronized void third() throws InterruptedException {
		secondLock.acquire();
		thirdLock.release();
	}

}
