package retos;

import java.io.File;
import java.util.Arrays;
 
public class Filtroavanzado {
 
    public static void main(String[] args) {
 
        if (args.length == 0) {
            System.out.println("Error: debes indicar una ruta como argumento.");
            return;
        }
 
        File ruta = new File(args[0]);
 
        if (!ruta.exists() || !ruta.isDirectory()) {
            System.out.println("Error: la ruta no existe o no es una carpeta.");
            return;
        }
 
        File[] archivos = ruta.listFiles();
 
        if (archivos == null) {
            System.out.println("Error: no se pudo leer el contenido de la carpeta.");
            return;
        }
 
        // Stream: filtrar .txt o .json con tamaño > 0, y mostrar el nombre en mayúsculas
        Arrays.stream(archivos)
              .filter(f -> f.isFile())
              .filter(f -> f.getName().endsWith(".txt") || f.getName().endsWith(".json"))
              .filter(f -> f.length() > 0)
              .map(f -> f.getName().toUpperCase())
              .forEach(nombre -> System.out.println(nombre));
    }
}
