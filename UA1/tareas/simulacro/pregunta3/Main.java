public class Main {

    public static void main(String[] args) throws Exception {

        long inicio = System.currentTimeMillis();

        Thread h1 = new Thread(new Tarea("Hilo 1", 1));
        Thread h2 = new Thread(new Tarea("Hilo 2", 2));
        Thread h3 = new Thread(new Tarea("Hilo 3", 3));

        h1.start();
        h2.start();
        h3.start();

        h1.join();
        h2.join();
        h3.join();

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo total: "
                + (fin - inicio) + " ms");
    }
}
