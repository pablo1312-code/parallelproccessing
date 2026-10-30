package retos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;
 
public class Gestorbitacora {
 
    public static void main(String[] args) {
 
        if (args.length < 2) {
            System.out.println("Uso: java GestorBitacora <ruta_archivo> <mensaje>");
            return;
        }
 
        String ruta = args[0];
        String mensaje = args[1];
 
        // 1. Escribir (append) con try-with-resources
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta, true))) {
            pw.println(LocalDateTime.now() + " - " + mensaje);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el archivo. Revisa el disco o la ruta.");
            return;
        }
 
        // 2. Leer y mostrar todo el contenido con Scanner
        System.out.println("--- Contenido de la bitácora ---");
 
        try (Scanner sc = new Scanner(new File(ruta))) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (IOException e) {
            System.out.println("No se ha podido leer el archivo. Revisa el disco o la ruta.");
        }
    }
}
 
