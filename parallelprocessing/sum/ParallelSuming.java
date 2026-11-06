package parallelprocessing.sum;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSuming {
	
	public static void main(String[] args) {
		//Create a thread pool object, the threads in the pool are reusable 
		ExecutorService executor =
		     Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		
		System.out.println("the number of processors :" + Runtime.getRuntime().availableProcessors());
		//Define a runnable task for a thread 
		Runnable task = () -> {
		     System.out.println("thread name is :" + Thread.currentThread().getName());
	};
	    //Submit tasks to the thread pool
	    executor.submit(task);
	    executor.submit(task);
	    executor.submit(task);
	    executor.submit(task);
	    for (int i = 0; i< 20; i++) {
	    	 executor.submit(task);
	    }
	    
	    
	    
	    
	    
		Thread thread; 
		
		
	}
}


