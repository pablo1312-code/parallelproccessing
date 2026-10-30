package parallelprocessing.sum;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSuming {
	
	public static void main(String[] args) {
		ExecutorService executor =
		     Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		Runnable task = () -> {
		     System.out.println("thread name is :" + Thread.currentThread().getName());
	};
	
	    executor.submit(task);
		Thread thread; 
		
		
	}
}


