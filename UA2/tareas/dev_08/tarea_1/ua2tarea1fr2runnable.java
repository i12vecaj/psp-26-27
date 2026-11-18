package UA2.tareas.dev_08.tarea_1;

/*
 * FR2 (parte Runnable): igual que el FR1, pero con el acceso al contador
 *      sincronizado. Los hilos se lanzan mediante el interfaz Runnable.
 * Control de errores: InterruptedException con try/catch en el join().
 * Documentacion: comentarios en el codigo
 */
/**
 * Version SINCRONIZADA con el interfaz Runnable. La tarea (sumar 1000 veces)
 * va en una clase que implementa Runnable y se la paso a un objeto Thread,
 * que es quien la ejecuta.
 *
 * Funcionamiento: la suma esta en un metodo estatico synchronized, igual que
 * en la version con Thread, asi que solo entra un hilo a la vez.
 *
 * Diferencias con el FR1: el resultado es siempre 5000 y no se pierde ningun
 * incremento.
 *
 * Diferencias con la version Thread: el resultado es el mismo. Cambia solo la
 * forma de crear el hilo. Con Runnable la tarea esta separada del hilo (la
 * clase no gasta su unica herencia en Thread y la misma tarea se puede pasar
 * a varios hilos). Con Thread la clase ES el hilo.
 */
public class ua2tarea1fr2runnable {

	private static final int NUM_HILOS = 5;
	private static final int INCREMENTOS = 1000;
	private static final int RONDAS = 20;

	private static int contador = 0;

	// Solo un hilo a la vez puede ejecutar este metodo
	private static synchronized void incrementar() {
		contador++;
	}

	// Tarea que implementa Runnable: no es un hilo, solo lo que hace el hilo
	static class TareaIncrementar implements Runnable {

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
			hilos[i] = new Thread(new TareaIncrementar(), "hilo-" + (i + 1));
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
