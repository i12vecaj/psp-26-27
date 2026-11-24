/**
 * FUNCIONAMIENTO:
 * Se lanzan 5 hilos que comparten la variable estatica "contador".
 * Cada hilo hace 1000 incrementos de 1 (contador++), asi que el resultado
 * esperado es 5 * 1000 = 5000.
 *
 * El acceso a "contador" NO esta sincronizado. Por lo que los resultados de cada una fallan con lo esperado.
 * A veces otras veces si dan bien o solo fallan algunas lo cual lo hace super inestable.
 */
public class ua2tarea1fr1 {

    private static final int NUM_HILOS = 5;
    private static final int INCREMENTOS = 1000;
    private static final int REPETICIONES = 10;

    // Variable compartida por todos los hilos (sin proteger)
    private static int contador = 0;

    /** Hilo que incrementa el contador sin ningun tipo de control. */
    static class HiloSinControl extends Thread {

        HiloSinControl(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                int valor = contador;
                contador = valor + 1;
            }
        }
    }

    public static void main(String[] args) {
        int esperado = NUM_HILOS * INCREMENTOS;
        int fallos = 0;

        for (int r = 1; r <= REPETICIONES; r++) {
            contador = 0;
            Thread[] hilos = new Thread[NUM_HILOS];

            try {
                for (int i = 0; i < NUM_HILOS; i++) {
                    hilos[i] = new HiloSinControl("hilo-" + (i + 1));
                    hilos[i].start();
                }
                // Esperamos a que terminen TODOS antes de mirar el resultado
                for (Thread hilo : hilos) {
                    hilo.join();
                }
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt(); // restauramos el estado de interrupcion
                return;
            } catch (IllegalThreadStateException e) {
                System.err.println("Error al arrancar un hilo: " + e.getMessage());
                return;
            }

            boolean correcto = contador == esperado;
            if (!correcto) {
                fallos++;
            }
            System.out.println("Ejecucion " + r + ": contador = " + contador
                    + (correcto ? "  (correcto)" : "  (INCORRECTO, se han perdido "
                    + (esperado - contador) + " incrementos)"));
        }

        System.out.println("\nResultado esperado: " + esperado);
        System.out.println("Ejecuciones incorrectas: " + fallos + " de " + REPETICIONES);
    }
}
