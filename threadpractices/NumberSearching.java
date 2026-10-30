package threadpractices;

public class NumberSearching {
	public static void main(String[] args) {
		//create an array initialized with random numbers 
		
		//create a thread 
		Thread t = new Thread(new Runnable() {
			
			@Override 
			public void run() {
				System.out.println("this is a new task");
			}
		});
		
		t.start();
	}
}

	


