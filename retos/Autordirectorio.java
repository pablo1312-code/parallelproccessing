package retos;

import java.io.File;

public class Autordirectorio {

	 public static void main(String[] args) {
		 
	        // 1. Comprobar que nos han pasado un argumento
	        if (args.length == 0) {
	            System.out.println("Error: debes indicar una ruta como argumento.");
	            return;
	        }
	 
	        File ruta = new File(args[0]);
	 
	        // 2. Comprobar que existe
	        if (!ruta.exists()) {
	            System.out.println("Error: la ruta no existe.");
	            return;
	        }
	 
	        // 3. Comprobar que es un directorio
	        if (!ruta.isDirectory()) {
	            System.out.println("Error: la ruta no es una carpeta.");
	            return;
	        }
	 
	        // 4. Listar los archivos
	        File[] archivos = ruta.listFiles();
	 
	        if (archivos == null) {
	            System.out.println("Error: no se pudo leer el contenido de la carpeta.");
	            return;
	        }
	 
	        System.out.println("Contenido de " + ruta.getAbsolutePath() + ":");
	 
	        for (File f : archivos) {
	            if (f.isFile()) {
	                double kb = f.length() / 1024.0;
	                System.out.printf("%s - %.2f KB%n", f.getName(), kb);
	            }
	        }
	    }
	}
	 
