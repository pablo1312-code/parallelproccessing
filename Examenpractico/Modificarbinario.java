package Examenpractico;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;

public class Modificarbinario {

    public static void main(String[] args) {
        try {
            RandomAccessFile archivo = new RandomAccessFile("datos.bin", "rw");

            // Escribir 10 enteros (1, 2, 3... 10)
            for (int i = 1; i <= 10; i++) {
                archivo.writeInt(i);
            }

            // Mostrar los números antes de modificar
            System.out.println("Antes de la modificación:");
            archivo.seek(0);
            for (int i = 0; i < 10; i++) {
                System.out.println(archivo.readInt());
            }

            // Pedir el nuevo número al usuario
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nuevo valor para el tercer número: ");
            int nuevo = sc.nextInt();

            // Cada entero ocupa 4 bytes, el tercero empieza en la posición 8
            archivo.seek(8);
            archivo.writeInt(nuevo);

            // Mostrar los números después de modificar
            System.out.println("Después de la modificación:");
            archivo.seek(0);
            for (int i = 0; i < 10; i++) {
                System.out.println(archivo.readInt());
            }

            archivo.close();
            sc.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}