package UA2;

public class ua2tarea1fr2runnable {

    static int contador = 0;

    static class MiTarea implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                incrementar();
            }
        }
    }

    // Método sincronizado compartido por todas las tareas.
    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        // Creamos cinco hilos utilizando Runnable
        for (int i = 0; i < 5; i++) {
            MiTarea tarea = new MiTarea();
            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        // Esperamos a que todos terminen
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error esperando a los hilos.");
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Contador final: " + contador);
        System.out.println("Resultado esperado: 5000");
    }
}
