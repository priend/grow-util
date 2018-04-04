package com.pre.roc.grow.voice.current;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShutdownableThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(ShutdownableThread.class);

	private AtomicBoolean isRunning = new AtomicBoolean(true);
	private CountDownLatch shutdownLatch = new CountDownLatch(1);

	/**
	 * An UncaughtExceptionHandler to register on every instance of this class.
	 * This is useful for testing, where AssertionExceptions in the thread may
	 * not cause the test to fail. Since one instance is used for all threads,
	 * it must be thread-safe.
	 */
	volatile public static UncaughtExceptionHandler funcaughtExceptionHandler = null;

	public ShutdownableThread(String name) {
		// The default is daemon=true so that these threads will not prevent
		// shutdown. We use this
		// default because threads that are running user code that may not clean
		// up properly, even
		// when we attempt to forcibly shut them down.
		this(name, true);
	}

	public ShutdownableThread(String name, boolean daemon) {
		super(name);
		this.setDaemon(daemon);
		if (funcaughtExceptionHandler != null)
			this.setUncaughtExceptionHandler(funcaughtExceptionHandler);
	}

	/**
	 * Implementations should override this method with the main body for the
	 * thread.
	 */
	public abstract void execute();

	/**
	 * Returns true if the thread hasn't exited yet and none of the shutdown
	 * methods have been invoked
	 */
	public boolean getRunning() {
		return isRunning.get();
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (RuntimeException e) {
			log.error("Thread {} exiting with uncaught exception: ", getName(), e);
			throw e;
		} finally {
			shutdownLatch.countDown();
		}
	}

	/**
	 * Shutdown the thread, first trying to shut down gracefully using the
	 * specified timeout, then forcibly interrupting the thread.
	 * 
	 * @param gracefulTimeout
	 *            the maximum time to wait for a graceful exit
	 * @param unit
	 *            the time unit of the timeout argument
	 */
	public void shutdown(long gracefulTimeout, TimeUnit unit) throws InterruptedException {
		boolean success = gracefulShutdown(gracefulTimeout, unit);
		if (!success)
			forceShutdown();
	}

	/**
	 * Attempt graceful shutdown
	 * 
	 * @param timeout
	 *            the maximum time to wait
	 * @param unit
	 *            the time unit of the timeout argument
	 * @return true if successful, false if the timeout elapsed
	 */
	public boolean gracefulShutdown(long timeout, TimeUnit unit) throws InterruptedException {
		startGracefulShutdown();
		return awaitShutdown(timeout, unit);
	}

	/**
	 * Start shutting down this thread gracefully, but do not block waiting for
	 * it to exit.
	 */
	public void startGracefulShutdown() {
		log.info("Starting graceful shutdown of thread {}", getName());
		isRunning.set(false);
	}

	/**
	 * Awaits shutdown of this thread, waiting up to the timeout.
	 * 
	 * @param timeout
	 *            the maximum time to wait
	 * @param unit
	 *            the time unit of the timeout argument
	 * @return true if successful, false if the timeout elapsed
	 * @throws InterruptedException
	 */
	public boolean awaitShutdown(long timeout, TimeUnit unit) throws InterruptedException {
		return shutdownLatch.await(timeout, unit);
	}

	/**
	 * Immediately tries to force the thread to shut down by interrupting it.
	 * This does not try to wait for the thread to truly exit because forcible
	 * shutdown is not always possible. By default, threads are marked as daemon
	 * threads so they will not prevent the process from exiting.
	 */
	public void forceShutdown() throws InterruptedException {
		log.info("Forcing shutdown of thread {}", getName());
		isRunning.set(false);
		interrupt();
	}
}
