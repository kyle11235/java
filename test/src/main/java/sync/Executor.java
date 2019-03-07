package sync;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Executor {

	// callable
	public void foo() {
		Callable<Integer> callable = new Callable<Integer>() {
			public Integer call() throws Exception {
				return null;
			}
		};
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		new Thread(future).start();
	}

	// fixed
	public void foo1() {
		int nThreads = 2;
		ExecutorService service = null;
		service = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		service = Executors.newFixedThreadPool(nThreads);
		service.execute(null);
		Future<Integer> future = service.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return null;
			}
		});
		service.shutdown();
	}

	// cached, multiple tasks
	public void foo2() {
		ExecutorService service = null;
		service = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		service = Executors.newCachedThreadPool();
		service.shutdown();
	}

	public void foo3() {
		ExecutorService service = null;
		service = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		service = Executors.newSingleThreadExecutor();
		service.shutdown();
	}

	public void foo4() {
		// pool of timer
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		service.shutdown();
	}

}
