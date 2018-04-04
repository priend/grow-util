package com.pre.roc.grow.voice.skill.efficient;

/**
 * 
 * @file GenerantEvent.java
 * @author ROCFLY ZHANGE PENGFEI
 * @dateTime 2014年7月16日 下午4:45:27
 */
public class GenerantSingle implements Generant {

	private long frequencyMillis = 1000L;
	private boolean interrupted;
	private Executor executor;
	private EventContext context;

	public GenerantSingle()
	{
		super();
	}

	/**
	 * 
	 * @param executor
	 * @param context
	 */
	public GenerantSingle(Executor executor, EventContext context)
	{
		this();
		this.context = context;
		this.executor = executor;
	}

	/**
	 * 
	 * 方法说明：增加任务
	 * 
	 */
	public void run() {
		interrupted = false;
		executor.start();
		threadTask().start();
	}

	/**
	 * 
	 * 方法说明：停止
	 * 
	 */
	public void interrupt() {
		interrupted = true;
		executor.shutdown();
	}

	/**
	 * 
	 * 方法说明：设置暂停
	 * 
	 * @param interrupted
	 */
	public void suspend() {
		interrupted = true;
	}

	/**
	 * 查找是否暂停
	 */
	@Override
	public Boolean isInterrupted() {
		return interrupted;
	}

	/**
	 * 
	 * 方法说明：结束时调用
	 * 
	 */
	public void disclose() {

	}

	/**
	 * 
	 * 方法说明：执行任务
	 * 
	 * @return Boolean
	 * @throws Exception
	 * 
	 */
	public Boolean handleTask() {
		return handleTask(new EventTask<Boolean>() {

			@Override
			public Boolean doInContext(EventContext ctx) throws Exception {
				return ctx.task();
			}
		});
	}

	/**
	 * @param eventTask
	 * @return
	 */
	private Boolean handleTask(EventTask<Boolean> eventTask) {
		try
		{
			return eventTask.doInContext(context);
		}
		catch (Exception e)
		{
			// logger.error("", ex);
		}
		finally
		{
			releaseContext(context);
		}
		return true;
	}

	/**
	 * @param ctx
	 */
	private void releaseContext(EventContext ctx) {
		if (ctx != null)
		{
			try
			{
				// ctx.close();
			}
			catch (Exception ex)
			{
				// logger.error("", ex);
			}
		}
	}

	/**
	 * 
	 * 方法说明：全局线程
	 * 
	 * @return Thread
	 */
	private Thread threadTask() {
		return new Thread(new Runnable() {
			public void run() {
				while (!interrupted)
				{
					GenerantSingle.this.executor.execute(new RunnableTask());
				}
			}
		});
	}

	/**
	 * 
	 * @file GenerantEvent.java
	 * @author ROCFLY ZHANGE PENGFEI
	 * @dateTime 2014年7月16日 下午4:53:34
	 */
	class RunnableTask implements Runnable {
		boolean isTask = true;

		/**
		 * 
		 */
		public void run() {
			try
			{
				do
				{
					isTask = handleTask();
					Thread.sleep(frequencyMillis);
				} while (isTask);
				{
					disclose();
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public void setFrequencyMillis(Long frequencyMillis) {
		this.frequencyMillis = frequencyMillis;
	}

	/**
	 * 
	 * @param executor
	 */
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	/**
	 * 
	 * @param context
	 */
	public void setContext(EventContext context) {
		this.context = context;
	}
}
