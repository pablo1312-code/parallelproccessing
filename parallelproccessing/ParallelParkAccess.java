package parallelproccessing;

public class ParallelParkAccess {
	public static void main(String[] args) {

		Aparcamiento aparcamiento1 = new Aparcamiento("Madrid", "Park1", 1000);
		Aparcamiento aparcamiento2 = new Aparcamiento("Sevilla", "Park2", 2000);
		Empresa empresa = new Empresa("paralel");
		empresa.registrarAparcamiento(aparcamiento1);
		empresa.registrarAparcamiento(aparcamiento2);
		System.out.println("before executing the threads");

		int numOfThread = 500;
		Thread[] threads = new Thread[numOfThread];
		for (int i = 0; i < numOfThread; i++) {
			Thread t = new EntranceJob(aparcamiento1, "Miguel" + i);
			threads[i] = t;
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		Thread t1 = new EntranceJob(aparcamiento1, "Miguel");
		Thread t2 = new EntranceJob(aparcamiento1, "Justo");
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(aparcamiento1.getCochesAparcados());

	}

}
