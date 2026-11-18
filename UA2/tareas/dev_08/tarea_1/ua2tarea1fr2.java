package UA2.tareas.dev_08.tarea_1;

/*
 * FR2 (parte Thread): igual que el FR1, pero con el acceso al contador
 *      sincronizado. Los hilos se lanzan mediante la clase Thread.
 * Control de errores: InterruptedException con try/catch en el join().
 * Documentacion: comentarios en el codigo
 */
/**
 * Version SINCRONIZADA con la clase Thread. Los 5 hilos siguen sumando 1000
 * veces a la misma variable, pero ahora la suma esta en un metodo synchronized.
 *
 * Funcionamiento: al ser un metodo estatico synchronized, el cerrojo es el de
 * la clase. Solo un hilo puede estar dentro de incrementar() a la vez, asi que
 * leer, sumar y escribir ocurre sin que nadie se cuele en medio.
 *
 * Diferencias con el FR1: el resultado es siempre 5000, en todas las rondas.
 * Ya no se pierden incrementos. A cambio, los hilos tienen que esperarse unos
 * a otros para entrar al metodo, asi que se pierde algo de paralelismo (con
 * tan pocas operaciones apenas se nota).
 */
public class ua2tarea1fr2 {

	private static final int NUM_HILOS = 5;
	private static final int INCREMENTOS = 1000;
	private static final int RONDAS = 20;

	private static int contador = 0;

	// Solo un hilo a la vez puede ejecutar este metodo
	private static synchronized void incrementar() {
		contador++;
	}

	// Hilo creado extendiendo de Thread
	static class Incrementador extends Thread {

		Incrementador(String nombre) {
			super(nombre);
		}

		@Override
		public void run() {
			for (int i = 0; i < INCREMENTOS; i++) {
				incrementar();
			}
		}
	}

	private static int ejecutarRonda() throws InterruptedException {
		contador = 0;
		Thread[] hilos = new Thread[NUM_HILOS];

		for (int i = 0; i < NUM_HILOS; i++) {
			hilos[i] = new Incrementador("hilo-" + (i + 1));
			hilos[i].start();
		}
		for (Thread hilo : hilos) {
			hilo.join();
		}
		return contador;
	}

	public static void main(String[] args) {
		int esperado = NUM_HILOS * INCREMENTOS;

		try {
			int obtenido = ejecutarRonda();
			System.out.println("Esperado: " + esperado + " | Obtenido: " + obtenido);

			int fallos = 0;
			for (int i = 0; i < RONDAS; i++) {
				if (ejecutarRonda() != esperado) {
					fallos++;
				}
			}
			System.out.println("En " + RONDAS + " rondas mas, el resultado fue incorrecto " + fallos + " veces");
		} catch (InterruptedException e) {
			// Se restaura la marca de interrupcion y se sale con error
			Thread.currentThread().interrupt();
			System.err.println("El hilo principal fue interrumpido mientras esperaba a los hilos");
			System.exit(1);
		}
	}
}
