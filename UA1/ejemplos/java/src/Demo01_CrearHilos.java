/** S1 - Crear hilos: extends Thread, implements Runnable, lambda. start() vs run(). */
public class Demo01_CrearHilos {

    static class MiHilo extends Thread {
        MiHilo(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            System.out.println("  [extends Thread] en " + getName());
        }
    }

    static class MiTarea implements Runnable {
        @Override
        public void run() {
            System.out.println("  [implements Runnable] en " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main: " + Thread.currentThread().getName());

        Thread a = new MiHilo("hilo-A");
        Thread b = new Thread(new MiTarea(), "hilo-B");
        Thread c = new Thread(() -> System.out.println("  [lambda] en " + Thread.currentThread().getName()), "hilo-C");
        a.run();
        b.start();
        c.start();
        a.join();
        b.join();
        c.join();

        System.out.println("\nERROR típico: llamar a run() NO crea un hilo nuevo");
        new MiHilo("hilo-D").run(); // se ejecuta en 'main'
        System.out.println("  (getName() de arriba es 'hilo-D' pero el hilo real fue: "
                + Thread.currentThread().getName() + ")");
    }
}