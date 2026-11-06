package ejercicio5;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelSearching3 {

    public static void main(String[] args) throws InterruptedException {

        int[] numeros = new int[10_000_000];
        Random random = new Random();
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = random.nextInt(1_000_000);
        }

        int objetivo = numeros[numeros.length - 5];
        int numHilos = 4;
        int tamBloque = numeros.length / numHilos;
        AtomicInteger posicion = new AtomicInteger(-1);

        ExecutorService executor = Executors.newFixedThreadPool(numHilos);

        for (int i = 0; i < numHilos; i++) {
            int inicio = i * tamBloque;
            int fin = (i == numHilos - 1) ? numeros.length : inicio + tamBloque;

            executor.execute(() -> {
                for (int pos = inicio; pos < fin; pos++) {
                    if (numeros[pos] == objetivo) {
                        posicion.set(pos);
                        return;
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        if (posicion.get() != -1) {
            System.out.println("Número " + objetivo + " encontrado en la posición " + posicion.get());
        } else {
            System.out.println("Número " + objetivo + " no encontrado");
        }
    }
}
