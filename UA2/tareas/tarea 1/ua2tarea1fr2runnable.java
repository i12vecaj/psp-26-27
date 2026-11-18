public class ua2tarea1fr2runnable {

    static class RecursoSincronizadoRunnable {
        private int contador = 0;

        public synchronized void incrementar() {
            contador++;
        }

        public synchronized int getContador() {
            return contador;
        }
    }

    static class TareaRunnable implements Runnable {
        private RecursoSincronizadoRunnable recurso;

        public TareaRunnable(RecursoSincronizadoRunnable recurso) {
            this.recurso = recurso;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    recurso.incrementar();
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        RecursoSincronizadoRunnable recurso = new RecursoSincronizadoRunnable();
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(new TareaRunnable(recurso));
            hilos[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.err.println("Error en join: " + e.getMessage());
            }
        }

        System.out.println("Valor final obtenido: " + recurso.getContador());
    }
}