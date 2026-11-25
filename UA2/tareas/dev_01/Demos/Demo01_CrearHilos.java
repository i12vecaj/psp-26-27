/** Crear hilos de dos formas y esperar a que acaben. */
public class Demo01_CrearHilos {

    // Forma 1: extender Thread
    static class MiHilo extends Thread {
        @Override
        public void run() {
            System.out.println("MiHilo se ejecuta en: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread h1 = new MiHilo();                       // extends Thread
        Thread h2 = new Thread(() ->                    // Runnable (con lambda)
                System.out.println("Runnable se ejecuta en: " + Thread.currentThread().getName()));

        h1.start();
        h2.start();
        h1.join();   // esperar a que terminen
        h2.join();

        new MiHilo().run(); // OJO: run() NO crea un hilo, se ejecuta en main
        System.out.println("Fin en: " + Thread.currentThread().getName());
    }
}
