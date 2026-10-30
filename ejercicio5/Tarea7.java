package ejercicio5;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Tarea7 {

    // Cada cuántos elementos un hilo mira el flag "encontrado" (potencia de 2).
    // Leer una variable atómica en cada iteración es costoso; así se reduce el
    // coste y la cancelación sigue siendo prácticamente inmediata.
    private static final int INTERVALO_COMPROBACION = 1024;

    // ------------------------------------------------------------------
    // MÉTODO REUTILIZABLE: no usa variables estáticas compartidas, todo el
    // estado (flag, posición, pool) es local a cada llamada, así que se puede
    // invocar tantas veces como se quiera, con cualquier array y objetivo.
    // Devuelve la posición donde se encontró el objetivo, o -1 si no existe.
    // ------------------------------------------------------------------
    public static int buscarParalelo(int[] datos, int objetivo, int numHilosDeseados)
            throws InterruptedException {

        if (datos == null || datos.length == 0) {
            return -1;
        }

        // No tiene sentido crear más hilos que elementos
        final int numHilos = Math.max(1, Math.min(numHilosDeseados, datos.length));

        // Tamaño de bloque base; el último hilo se queda con el resto
        final int tamBloque = datos.length / numHilos;

        final AtomicBoolean encontrado = new AtomicBoolean(false);
        final AtomicInteger posicionEncontrada = new AtomicInteger(-1);

        ExecutorService pool = Executors.newFixedThreadPool(numHilos);

        try {
            for (int i = 0; i < numHilos; i++) {

                final int inicio = i * tamBloque;
                // El último hilo llega hasta el final del array (absorbe el resto)
                final int fin = (i == numHilos - 1) ? datos.length : inicio + tamBloque;

                pool.execute(() -> {
                    for (int pos = inicio; pos < fin; pos++) {

                        // Cancelación temprana: si otro hilo ya lo encontró, salimos
                        if ((pos & (INTERVALO_COMPROBACION - 1)) == 0 && encontrado.get()) {
                            return;
                        }

                        if (datos[pos] == objetivo) {
                            // Solo el primer hilo que lo encuentra guarda su posición
                            if (encontrado.compareAndSet(false, true)) {
                                posicionEncontrada.set(pos);
                            }
                            return;
                        }
                    }
                });
            }
        } finally {
            pool.shutdown(); // no se aceptan más tareas
        }

        // El hilo main espera a que TODOS los hilos terminen antes de devolver
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return posicionEncontrada.get();
    }

    // Versión secuencial, solo para comparar tiempos
    public static int buscarSecuencial(int[] datos, int objetivo) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] == objetivo) {
                return i;
            }
        }
        return -1;
    }

    // Genera un array de números aleatorios en el rango [0, maximo)
    public static int[] generarArray(int tamanio, int maximo) {
        Random generador = new Random();
        int[] datos = new int[tamanio];
        for (int i = 0; i < tamanio; i++) {
            datos[i] = generador.nextInt(maximo);
        }
        return datos;
    }

    // Ejecuta y mide una comparación secuencial vs paralelo
    private static void probar(String descripcion, int[] datos, int objetivo, int hilos)
            throws InterruptedException {

        System.out.println("\n--- " + descripcion + " (objetivo = " + objetivo + ") ---");

        long t0 = System.nanoTime();
        int posSec = buscarSecuencial(datos, objetivo);
        double msSec = (System.nanoTime() - t0) / 1_000_000.0;

        t0 = System.nanoTime();
        int posPar = buscarParalelo(datos, objetivo, hilos);
        double msPar = (System.nanoTime() - t0) / 1_000_000.0;

        System.out.printf("Secuencial: posición %d en %.3f ms%n", posSec, msSec);
        System.out.printf("Paralelo  : posición %d en %.3f ms (%d hilos)%n", posPar, msPar, hilos);

        if (posPar == -1) {
            System.out.println("Resultado: el número NO está en el array");
        } else if (datos[posPar] == objetivo) {
            System.out.println("Resultado: encontrado y verificado en la posición " + posPar);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 1. Detección automática del hardware
        int nucleos = Runtime.getRuntime().availableProcessors();
        System.out.println("Procesadores disponibles: " + nucleos);

        // 2. Array grande (se puede cambiar el tamaño sin tocar nada más)
        int tamanio = 10_000_000;
        int[] numeros = generarArray(tamanio, 1_000_000);
        System.out.println("Tamaño del array: " + tamanio);

        // Calentamiento de la JVM para que las mediciones sean más justas
        buscarParalelo(numeros, -1, nucleos);
        buscarSecuencial(numeros, -1);

        // 3. Pruebas
        // Caso típico: el número existe (se coge uno real del array, cerca del final)
        probar("Número existente", numeros, numeros[tamanio - 5], nucleos);

        // Peor caso: el número no existe, hay que recorrer todo el array
        probar("Número inexistente (peor caso)", numeros, -1, nucleos);

        // Caso límite: array pequeño con más hilos deseados que elementos
        int[] pequeno = {4, 8, 15, 16, 23, 42, 7};
        probar("Array pequeño, división no exacta", pequeno, 42, 16);
    }
}
