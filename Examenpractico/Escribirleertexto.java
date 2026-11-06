package Examenpractico;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class Escribirleertexto {

    public static void main(String[] args) {
        try {
            // Crear el archivo solo si no existe
            File fichero = new File("prueba.txt");
            if (!fichero.exists()) {
                fichero.createNewFile();
                System.out.println("Archivo creado.");
            }

            RandomAccessFile archivo = new RandomAccessFile(fichero, "rw");

            // Escribir un mensaje
            archivo.writeUTF("Hola, este es mi mensaje de prueba");

            // Volver al inicio del archivo y leer el mensaje
            archivo.seek(0);
            String mensaje = archivo.readUTF();
            System.out.println("Mensaje leído: " + mensaje);

            archivo.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}