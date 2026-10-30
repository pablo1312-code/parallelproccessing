package ejerciciojava3;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Ejercicio3 {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("prueba.dat", "rw")) {

            // Escritura: cada int ocupa 4 bytes
            for (int i = 1; i <= 10; i++) {
                raf.writeInt(i);
            }

            // Volver al principio del archivo
            raf.seek(0);

            // Lectura: hasta llegar al final del archivo
            while (raf.getFilePointer() < raf.length()) {
                System.out.println(raf.readInt());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
