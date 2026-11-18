public class ua2tarea1fr1 {

    static class RecursoCompartido {
        private int contador = 0;

        public void incrementar() {
            contador++;
        }

        public int getContador() {
            return contador;
        }
    }

    static class HiloContador extends Thread {
        private RecursoCompartido recurso;

        public HiloContador(RecursoCompartido recurso) {
            this.recurso = recurso;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    recurso.incrementar();
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        RecursoCompartido recurso = new RecursoCompartido();
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new HiloContador(recurso);
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