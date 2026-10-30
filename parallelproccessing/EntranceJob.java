package parallelproccessing;

public class EntranceJob extends Thread {
	
	public EntranceJob(Aparcamiento aparcamiento1, String string) {
		// TODO Auto-generated constructor stub
		
		this.aparcamiento = aparcamiento1; 
	}



	private Aparcamiento aparcamiento; 
	
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("Start the job with thread" + name);
		System.out.println(aparcamiento + " object address from thread");
//	try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
		
		for (int i = 0; i < 4000; i++) {
			aparcamiento.entrarCoche();
		}
		System.out.println("End the job with thread" + name);
		
	}
	}
//  initialize 


