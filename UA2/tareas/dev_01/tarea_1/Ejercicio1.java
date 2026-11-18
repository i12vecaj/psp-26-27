
public class Ejercicio1 extends Thread {

    static int contador = 0;

    public void run() {
        for (int i = 0; i < 1000; i++) {
            contador++;
        }
    }
    public static void main(String[] args) {
        Ejercicio1[] hilos = new Ejercicio1[5];
        try {
            for (int i = 0; i < 5; i++) {
                hilos[i] = new Ejercicio1();
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }
            System.out.println("Resultado esperado: 5000");
            System.out.println("Resultado obtenido: " + contador);

        } catch (InterruptedException e) {
            System.out.println("Error con los hilos");
            Thread.currentThread().interrupt();
        }
    }
}
