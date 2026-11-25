
public class ContadorThread extends Thread {

    static int contador = 0;

    // Incrementamos el contador de forma sincronizada
    public static synchronized void incrementar() {
        contador++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            incrementar();
        }
    }

    public static void main(String[] args) {

        ContadorThread[] hilos = new ContadorThread[5];

        // Creamos e iniciamos los hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new ContadorThread();
            hilos[i].start();
        }

        // Esperamos a que terminen
        try {
            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error al esperar los hilos");
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Resultado esperado: 5000");
        System.out.println("Resultado obtenido: " + contador);
    }
}
