
package UA2;

public class ua2tarea1fr2 {

    static int contador = 0;

    static class MiHilo extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                incrementar();
            }
        }
    }

    // Método sincronizado: solo un hilo puede incrementar
    // el contador a la vez utilizando el monitor de la clase.
    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {

        MiHilo[] hilos = new MiHilo[5];

        // Creamos e iniciamos los cinco hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new MiHilo();
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
