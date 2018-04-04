package com.pre.roc.grow.voice.skill.efficient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @file PoolExecutor.java
 * @author ROCFLY ZHANGE PENGFEI
 * @dateTime 2014年7月17日 下午5:38:47
 */
public class EventExecutor implements Executor {

	private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(1000);
	private RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
	private TimeUnit unit = TimeUnit.MILLISECONDS;

	private int minPoolSize = 10;
	private int maximumPoolSize = 100;
	private long keepAliveTime = 3000;

	private ThreadPoolExecutor executor;

	@Override
	public void start() {
		executor = new ThreadPoolExecutor(minPoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler);
	}

	@Override
	public void shutdown() {
		executor.shutdown();
	}

	@Override
	public void execute(Runnable command) {
		executor.execute(command);
	}

	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}

	public RejectedExecutionHandler getHandler() {
		return handler;
	}

	public void setHandler(RejectedExecutionHandler handler) {
		this.handler = handler;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

}
