package com.example.demo;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadExecutor extends ThreadPoolExecutor{
	
	private static LinkedBlockingQueue<Runnable> queue = null;

	private static ThreadExecutor threadExecutor = null;
	
	// 실행할 최소 Thread 수
	private static int coreSize = 8;
	// 최대 Thread 수
	private static int maxPoolSize = 300;

	private static int keepAliveTime = 10;
	// 처리 Queue
	private static int queueSize = 20;

	public ThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, CallerRunsPolicy callerRunsPolicy) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, callerRunsPolicy);
		// TODO Auto-generated constructor stub
	}
	
	
	public static ThreadExecutor getInstance() {
		
		if (threadExecutor == null) {
			synchronized (ThreadExecutor.class) {
				init();
				queue = new LinkedBlockingQueue<>(queueSize);
				
				threadExecutor = new ThreadExecutor(coreSize, maxPoolSize, keepAliveTime, SECONDS, queue,
						new ThreadPoolExecutor.CallerRunsPolicy());
			}

		}
		return threadExecutor;
		
	}
	
	private static void init() {
		
		coreSize = 8;
		maxPoolSize = 300;
		keepAliveTime = 10;
		queueSize = 20;
		
	}
	
	@Override
	protected void finalize() {
		
		if(threadExecutor != null) {
			// 취소된 모든 작업 제거
			threadExecutor.purge();
			//thread 종료
			threadExecutor.shutdown();
		}
		
		super.finalize();
	}
	
	
	public void add(Runnable r) {
		
		 try {
			threadExecutor.execute(r);
		 }catch(RejectedExecutionException  e) {
			 e.printStackTrace();
		 }
		 
	}
	
	public int getActiveCount() {
		
		int ret = 0;
		
		if(threadExecutor != null) {
			ret = threadExecutor.getActiveCount();
		}
		
		return ret;
		
	}


}
