public class ua2tarea1fr2 { //sincronizado, siempre da 5000 

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;
    static final int REPETICIONES = 100; // se repite para comprobar que nunca falla

    static int contador = 0; // compartido, protegido por incrementar()

    // static porque cada Thread es un objeto distinto: asi el cerrojo es la clase, comun a todos
    static synchronized void incrementar() {
        contador++;
    }

    static class HiloIncrementador extends Thread {
        HiloIncrementador(String nombre) { super(nombre); }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                incrementar();
            }
        }
    }

    // Lanza los hilos, espera a todos y devuelve el valor final
    static int ejecutarPrueba() throws InterruptedException {
        contador = 0;
        Thread[] hilos = new Thread[NUM_HILOS];
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new HiloIncrementador("hilo-" + (i + 1));
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
            int fallos = 0;
            for (int r = 1; r <= REPETICIONES; r++) {
                int resultado = ejecutarPrueba();
                if (r == 1) {
                    System.out.println("[Thread] Primera ejecucion -> esperado: " + esperado + " | obtenido: " + resultado);
                }
                if (resultado != esperado) fallos++;
            }
            System.out.println("[Thread] Repetido " + REPETICIONES + " veces -> ejecuciones con resultado incorrecto: " + fallos);
            System.out.println(fallos == 0
                    ? "Con synchronized no se pierde ningun incremento."
                    : "Error inesperado: la sincronizacion no ha funcionado.");
        } catch (InterruptedException e) {
            System.err.println("Error: el hilo main fue interrumpido mientras esperaba a los hilos: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
