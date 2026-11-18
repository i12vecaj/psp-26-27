package UA2.tareas.dev_08.tarea_1;

/*
 * FR1: lanza 5 hilos y cada uno incrementa 1000 veces una variable contador
 *      compartida por todos. Se comprueba el resultado final.
 * Control de errores: InterruptedException con try/catch en el join().
 * Documentacion: comentarios en el codigo
 */
/**
 * Version SIN sincronizar. Lanzo 5 hilos (clase Thread) que suman 1000 veces
 * a la misma variable estatica. El resultado esperado es 5 x 1000 = 5000.
 *
 * Reflexion: contador++ parece una sola operacion, pero son tres: leer el
 * valor, sumarle 1 y volver a escribirlo. Si dos hilos leen el mismo valor a
 * la vez, los dos escriben el mismo resultado y se pierde un incremento. Esto
 * se llama condicion de carrera, y por eso el resultado puede ser menor que
 * 5000 y cambiar de una ejecucion a otra.
 *
 * Lo que he observado: con contador++ tal cual, la mayoria de las veces sale
 * 5000 y solo de vez en cuando falla. Es porque cada hilo hace muy poco trabajo
 * (1000 sumas) y suele terminar antes de que otro empiece a solaparse. Que
 * salga bien NO significa que el codigo sea correcto, solo que no ha habido
 * mala suerte. Por eso el programa tiene dos pruebas:
 *   1) contador++ normal, repetido varias rondas para ver si alguna falla.
 *   2) la misma suma separada en leer / ceder turno / escribir, que obliga a
 *      que los hilos se solapen. Aqui se pierden muchos incrementos siempre.
 */
public class ua2tarea1fr1 {

	private static final int NUM_HILOS = 5;
	private static final int INCREMENTOS = 1000;
	private static final int RONDAS = 20;

	// Variable compartida por todos los hilos, sin ninguna proteccion
	private static int contador = 0;

	// Hilo que suma 1000 veces al contador compartido
	static class Incrementador extends Thread {

		private final boolean forzarSolape;

		Incrementador(String nombre, boolean forzarSolape) {
			super(nombre);
			this.forzarSolape = forzarSolape;
		}

		@Override
		public void run() {
			for (int i = 0; i < INCREMENTOS; i++) {
				if (forzarSolape) {
					// Leer, dejar pasar a otro hilo y escribir: se pierden sumas
					int copia = contador;
					Thread.yield();
					contador = copia + 1;
				} else {
					contador++;
				}
			}
		}
	}

	// Lanza los 5 hilos, espera a que acaben y devuelve el valor final
	private static int ejecutarRonda(boolean forzarSolape) throws InterruptedException {
		contador = 0;
		Thread[] hilos = new Thread[NUM_HILOS];

		for (int i = 0; i < NUM_HILOS; i++) {
			hilos[i] = new Incrementador("hilo-" + (i + 1), forzarSolape);
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
			System.out.println("--- Prueba 1: contador++ normal ---");
			int obtenido = ejecutarRonda(false);
			System.out.println("Esperado: " + esperado + " | Obtenido: " + obtenido);

			if (obtenido != esperado) {
				System.out.println("Se han perdido " + (esperado - obtenido) + " incrementos");
			} else {
				System.out.println("Esta vez ha coincidido, pero no esta garantizado");
			}

			int fallos = 0;
			for (int i = 0; i < RONDAS; i++) {
				if (ejecutarRonda(false) != esperado) {
					fallos++;
				}
			}
			System.out.println("En " + RONDAS + " rondas mas, el resultado fue incorrecto " + fallos + " veces");

			System.out.println("--- Prueba 2: solapamiento forzado ---");
			obtenido = ejecutarRonda(true);
			System.out.println("Esperado: " + esperado + " | Obtenido: " + obtenido);
			System.out.println("Se han perdido " + (esperado - obtenido) + " incrementos");
		} catch (InterruptedException e) {
			// Se restaura la marca de interrupcion y se sale con error
			Thread.currentThread().interrupt();
			System.err.println("El hilo principal fue interrumpido mientras esperaba a los hilos");
			System.exit(1);
		}
	}
}
