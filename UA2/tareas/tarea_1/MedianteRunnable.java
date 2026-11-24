package actividad1UA2;

/** La principal diferencia entre ambos es que runnable crea una instacia para los cinco hilos y en
 * Thread los hilos se crean de forma que ninguno sabe lo que hace el otro.
 */

public class MedianteRunnable implements Runnable {
    int contador_global = 0;
    final Object llave = new Object();

    @Override
    public void run() {
        synchronized (llave) {
            contador_global += 1000;
            System.out.println(Thread.currentThread().getName() + " | Contador: " + contador_global);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MedianteRunnable tarea = new MedianteRunnable();

        Thread h1 = new Thread(tarea, "Hilo 1");
        Thread h2 = new Thread(tarea, "Hilo 2");
        Thread h3 = new Thread(tarea, "Hilo 3");
        Thread h4 = new Thread(tarea, "Hilo 4");
        Thread h5 = new Thread(tarea, "Hilo 5");

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
        System.out.println("RESULTADO FINAL: " + tarea.contador_global);
    }
}