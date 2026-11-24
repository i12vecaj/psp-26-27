package actividad1UA2;

/** Esto aplica a ambos Mediante... Los dos son escalonados en su resultado por usar syncronized,
 * que tienen la capacidad de bloquear el uso de los otros hilos y capar unicamente a su suma de una en una.
 */

public class MedianteThread extends Thread {
    static int contador_global = 0;
    static final Object llave = new Object();

    public MedianteThread(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        synchronized (llave) {
            contador_global += 1000;
            System.out.println(getName() + " | Contador: " + contador_global);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MedianteThread h1 = new MedianteThread("Hilo 1");
        MedianteThread h2 = new MedianteThread("Hilo 2");
        MedianteThread h3 = new MedianteThread("Hilo 3");
        MedianteThread h4 = new MedianteThread("Hilo 4");
        MedianteThread h5 = new MedianteThread("Hilo 5");

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        h1.join();
        h2.join();
        h3.join();
        h4.join();
        h5.join();

        System.out.println();
        System.out.println("RESULTADO FINAL: "+contador_global);
    }
}