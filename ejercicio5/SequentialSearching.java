package ejercicio5;

import java.util.Random;

public class SequentialSearching {

    public static void main(String[] args) {

        Random generador = new Random();

        // 1. Creamos el array con números aleatorios
        int[] numeros = new int[1000];

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = generador.nextInt(1000);
        }

        // Número que queremos buscar
        int objetivo = 500;

        // 2. Creamos el hilo
        Thread buscador = new Thread(new Runnable() {

            @Override
            public void run() {

                // 3. El hilo busca el número
                System.out.println("Número seleccionado: " + objetivo);

                System.out.println("Iniciando búsqueda...");

                boolean localizado = false;

                for (int posicion = 0; posicion < numeros.length; posicion++) {

                    if (numeros[posicion] == objetivo) {
                        System.out.println(
                            "Número " + objetivo + " encontrado en la posición " + posicion
                        );

                        localizado = true;
                    }
                }

                if (!localizado) {
                    System.out.println("No se ha localizado el número");
                }
            }
        });

        // Iniciamos el hilo
        buscador.start();
    }
}
