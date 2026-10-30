package parallelproccessing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Empresa {
    private final String nombre;
    private final List<Aparcamiento> aparcamientos;

    public Empresa(String nombre) {
        this.nombre = nombre;
        this.aparcamientos = new CopyOnWriteArrayList<>();
    }

    /** Registra un nuevo aparcamiento en la empresa. */
    public void registrarAparcamiento(Aparcamiento aparcamiento) {
        aparcamientos.add(aparcamiento);
    }

    /** Busca todos los aparcamientos que la empresa tiene en una ciudad dada. */
    public List<Aparcamiento> buscarPorCiudad(String ciudad) {
        return aparcamientos.stream()
                .filter(a -> a.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
    }

    /** Devuelve la lista completa de aparcamientos (copia defensiva). */
    public List<Aparcamiento> getAparcamientos() {
        return new ArrayList<>(aparcamientos);
    }

    public String getNombre() {
        return nombre;
    }

    /** Consulta general: imprime el estado de todos los aparcamientos. */
    public void consultarGeneral() {
        System.out.println("===== Estado general de " + nombre + " =====");
        int totalCoches = 0;
        int totalCapacidad = 0;
        for (Aparcamiento a : aparcamientos) {
            System.out.println("  - " + a);
            totalCoches += a.getCochesAparcados();
            totalCapacidad += a.getCapacidadMaxima();
        }
        System.out.println("Total: " + totalCoches + "/" + totalCapacidad + " plazas ocupadas");
        System.out.println("=============================================");
    }
}





