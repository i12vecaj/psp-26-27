
public class ContadorSinSincronizar {

    static int contador = 0;

    public static void main(String[] args) {

        Thread[] hilos = new Thread[5];

        // Creamos los 5 hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    contador++;
                }
            });
        }

        // Iniciamos los hilos
        for (int i = 0; i < 5; i++) {
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
