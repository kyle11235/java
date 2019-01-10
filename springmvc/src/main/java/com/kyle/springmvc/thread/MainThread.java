package com.kyle.springmvc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread {

	public static void main(String[] args) throws ClassNotFoundException {

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

		// newCachedThreadPool.invokeAll(tasks)

		// Executors.newCachedThreadPool(threadFactory);

		int i = 3;
		System.out.println(i + i + "20");// 620

	}

}
