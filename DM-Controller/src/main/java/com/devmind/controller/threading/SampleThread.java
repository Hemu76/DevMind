package com.devmind.controller.threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SampleThread {
	static CountDownLatch latch = new CountDownLatch(1);
	public static Object LOCK = new Object();
	public static volatile boolean cool = true;

	public static void main(String[] args) {
		ThreadFactory fact = r -> {
			Thread t = new Thread(r);
			
			t.setName("Sample-Testing");
			return t;
		};
		ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),fact);
		
		Thread t1 = new Thread(()->{
			while(!Thread.currentThread().isInterrupted()) {
				System.out.println("Intrupt Mechanism");
			}
			System.out.println("Thread is Intrupted");
		});
		
		t1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t1.interrupt();
		
		Thread t2 = new Thread(()->{
			while(cool) {
				System.out.println("Use of Volatile");
			}
		});
		
		t2.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cool = false;
		
		es.execute(new FirstThread());
		es.execute(new SecondThread());
		es.execute(new ThirdThread());
		es.execute(new FourthThread());
		es.execute(()-> {
			System.out.println("Coool");
		});
		
		
		
		Runnable task = ()->{
			System.out.println("Runnable");
		};
		
		es.execute(task);
		es.execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("Runnable1");
				
			}
			
		});
		
		es.shutdown();
		
        int cores = Runtime.getRuntime().availableProcessors();
		
		 int corePoolSize = cores;
	        int maxPoolSize  = cores;
	        int queueCapacity = 50;

	        BlockingQueue<Runnable> queue =
	                new ArrayBlockingQueue<>(queueCapacity);

	        ThreadFactory threadFactory = r -> {
	            Thread t = new Thread(r);
	            t.setName("worker-" + t.getId());
	            return t;
	        };

	        RejectedExecutionHandler handler =
	                new ThreadPoolExecutor.AbortPolicy();

	        ThreadPoolExecutor executor =
	                new ThreadPoolExecutor(
	                        corePoolSize,
	                        maxPoolSize,
	                        60,
	                        TimeUnit.SECONDS,
	                        queue,
	                        threadFactory,
	                       handler
	                );

	        // Optional but VERY useful
	        executor.allowCoreThreadTimeOut(true);

	        // Submit tasks
	        for (int i = 1; i <= 50; i++) {
	            int taskId = i;
	            executor.execute(() -> {
	                System.out.println(
	                        Thread.currentThread().getName()
	                                + " executing task " + taskId
	                );
	                simulateIO();
	            });
	        }

	        executor.shutdown();
	    }
	    
	

	public static void simulateIO() {
    try {
        Thread.sleep(1000); // simulate DB / HTTP call
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

	public static class FirstThread implements Runnable {

		@Override
		public void run() {
			synchronized(LOCK) {
				try {
					System.out.println("Thread is Waiting");
					LOCK.wait();
					System.out.println("Thread is Resumed");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName());
		}
	}

	public static class SecondThread implements Runnable {

		@Override
		public void run() {
			synchronized(LOCK) {
				LOCK.notify();
			}
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	public static class ThirdThread implements Runnable {

		@Override
		public void run() {
			System.out.println("Latch is waiting");
			try {
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	public static class FourthThread implements Runnable {

		@Override
		public void run() {
			latch.countDown();
			System.out.println(Thread.currentThread().getName());
		}
	}
}
