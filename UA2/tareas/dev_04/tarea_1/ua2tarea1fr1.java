public class ua2tarea1fr1 { //  5 hilos x 1000 sin sincronizar. A veces da menos de 5000: contador++ no es atomico

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;
    static final int REPETICIONES = 100; // el fallo sale pocas veces, asi que se repite la prueba

    static int contador = 0; // compartido por todos los hilos, sin proteger

    static class HiloIncrementador extends Thread {
        HiloIncrementador(String nombre) { super(nombre); }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                contador++; // aqui se pisan los hilos
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
            int minimo = esperado;
            for (int r = 1; r <= REPETICIONES; r++) {
                int resultado = ejecutarPrueba();
                if (r == 1) {
                    System.out.println("Primera ejecucion -> esperado: " + esperado + " | obtenido: " + resultado);
                }
                if (resultado != esperado) fallos++;
                minimo = Math.min(minimo, resultado);
            }

            System.out.println("Repetido " + REPETICIONES + " veces -> ejecuciones con resultado incorrecto: "
                    + fallos + " | valor mas bajo: " + minimo);
            System.out.println(fallos > 0
                    ? "Se han perdido incrementos: hay condicion de carrera."
                    : "Esta vez no ha fallado, pero sin sincronizar el resultado NO esta garantizado.");
        } catch (InterruptedException e) {
            System.err.println("Error: el hilo main fue interrumpido mientras esperaba a los hilos: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
