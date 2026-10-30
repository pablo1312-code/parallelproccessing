package ejercicio5;
 
import java.util.Random;
 
public class ParallelSearching1 {
 
    // Variable compartida por los dos hilos: avisa de que el número ya se encontró
    static volatile boolean encontrado = false;
 
    public static void main(String[] args) {
 
        Random generador = new Random();
 
        // 1. Creamos el array con números aleatorios
        int[] numeros = new int[1000];
 
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = generador.nextInt(1000);
        }
 
        // Número que queremos buscar
        int objetivo = 500;
 
        // Punto donde dividimos el array en dos mitades
        int mitad = numeros.length / 2;
 
        System.out.println("Número seleccionado: " + objetivo);
 
        System.out.println("Iniciando búsqueda...");
 
        // 2. Creamos el primer hilo (primera mitad)
        Thread buscador1 = new Thread(new Runnable() {
 
            @Override
            public void run() {
 
                // 3. El hilo busca el número en la primera mitad
                boolean localizado = false;
 
                for (int posicion = 0; posicion < mitad; posicion++) {
 
                    // Si el otro hilo ya lo encontró, este se para
                    if (encontrado) {
                        System.out.println("Hilo 1 detenido: el otro hilo ya encontró el número");
                        break;
                    }
 
                    if (numeros[posicion] == objetivo) {
                        System.out.println(
                            "Hilo 1: Número " + objetivo + " encontrado en la posición " + posicion
                        );
 
                        localizado = true;
                        encontrado = true;
                        break;
                    }
                }
 
                if (!localizado && !encontrado) {
                    System.out.println("Hilo 1: No se ha localizado el número en su mitad");
                }
            }
        });
 
        // 2. Creamos el segundo hilo (segunda mitad)
        Thread buscador2 = new Thread(new Runnable() {
 
            @Override
            public void run() {
 
                // 3. El hilo busca el número en la segunda mitad
                boolean localizado = false;
 
                for (int posicion = mitad; posicion < numeros.length; posicion++) {
 
                    // Si el otro hilo ya lo encontró, este se para
                    if (encontrado) {
                        System.out.println("Hilo 2 detenido: el otro hilo ya encontró el número");
                        break;
                    }
 
                    if (numeros[posicion] == objetivo) {
                        System.out.println(
                            "Hilo 2: Número " + objetivo + " encontrado en la posición " + posicion
                        );
 
                        localizado = true;
                        encontrado = true;
                        break;
                    }
                }
 
                if (!localizado && !encontrado) {
                    System.out.println("Hilo 2: No se ha localizado el número en su mitad");
                }
            }
        });
 
        // Iniciamos los hilos
        buscador1.start();
        buscador2.start();
    }
}
