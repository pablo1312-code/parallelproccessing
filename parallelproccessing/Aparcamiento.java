package parallelproccessing;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Aparcamiento {

	/**
	 * Representa un aparcamiento físico de la empresa.
	 *
	 * Esta clase es el punto CRÍTICO del sistema multihilo: varios hilos (entradas
	 * y salidas de coches) acceden concurrentemente al contador de plazas ocupadas.
	 * Para garantizar la integridad de los datos se usa el monitor intrínseco de
	 * Java (synchronized) junto con wait()/ notifyAll(): si el aforo está completo,
	 * un coche que quiere entrar se queda "esperando" hasta que otro sale y libera
	 * una plaza, en vez de simplemente rechazar la entrada.
	 */

	private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

	// Contador para dar un identificador legible a cada coche en los logs,
	// sin necesitar una clase Vehiculo aparte.
	private static final AtomicInteger CONTADOR_COCHES = new AtomicInteger(0);

	private final String ciudad;
	private final String nombre;
	private final int capacidadMaxima;

	// Variable de estado compartida -> sección crítica
	private int cochesAparcados;

	/**
	 * Constructor corto: nombre, capacidad máxima y coches ya aparcados al
	 * arrancar.
	 */
	public Aparcamiento(String nombre, int string, int contadorInicial) {
		this("", nombre, string, contadorInicial);
	}

	/**
	 * Constructor completo, con ciudad, para poder usar Empresa.buscarPorCiudad().
	 */
	public Aparcamiento(String ciudad, String nombre, int capacidadMaxima, int contadorInicial) {
		if (capacidadMaxima <= 0) {
			throw new IllegalArgumentException("La capacidad máxima debe ser positiva");
		}
		if (contadorInicial < 0 || contadorInicial > capacidadMaxima) {
			throw new IllegalArgumentException("El contador inicial debe estar entre 0 y la capacidad máxima");
		}
		this.ciudad = ciudad;
		this.nombre = nombre;
		this.capacidadMaxima = capacidadMaxima;
		this.cochesAparcados = contadorInicial;
	}

	public Aparcamiento(String nombre2, String string, int contadorInicial) {
		this.ciudad = "";
		this.nombre = "";
		// TODO Auto-generated constructor stub
		this.capacidadMaxima = 0;
	}

	/**
	 * Incrementa el contador respetando el aforo máximo. Si el aparcamiento está
	 * lleno, el hilo llamante se bloquea (wait) hasta que salirCoche() libere una
	 * plaza y avise con notifyAll().
	 */
	public  void entrarCoche() {

		cochesAparcados++;
	}

	/**
	 * Decrementa el contador sin permitir valores negativos. Si ya está a 0, no
	 * hace nada (solo registra un aviso).
	 */
	public synchronized void salirCoche() {
		if (cochesAparcados <= 0) {
			log("AVISO: intento de salida sin coches aparcados en " + nombre);
			return;
		}
		cochesAparcados--;
		log("SALE   coche <- " + nombre + " (" + cochesAparcados + "/" + capacidadMaxima + ")");
		notifyAll(); // despierta a los hilos que esperaban plaza libre
	}

	/**
	 * Consulta el estado actual (también sincronizada, para lecturas consistentes).
	 */
	public synchronized int getCochesAparcados() {
		return cochesAparcados;
	}

	public synchronized boolean estaLleno() {
		return cochesAparcados >= capacidadMaxima;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}

	private void log(String mensaje) {
		String hora = LocalTime.now().format(HORA);
		System.out.printf("[%s] (%s) %s%n", hora, Thread.currentThread().getName(), mensaje);
	}

	@Override
	public synchronized String toString() {
		return String.format("Aparcamiento[%s] %d/%d plazas ocupadas", nombre, cochesAparcados, capacidadMaxima);
	}

	public char[] getCars() {
		// TODO Auto-generated method stub
		return null;
	}


}
