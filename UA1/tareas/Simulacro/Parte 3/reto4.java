class MiTarea implements Runnable {
    private String nombre;
    private int segundos;

    public MiTarea(String nombre, int segundos) {
        this.nombre = nombre;
        this.segundos = segundos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(segundos * 1000L);
            System.out.println("Hilo " + nombre + " terminó");
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}

public class Reto4 {
    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();
        Thread[] hilos = new Thread[3];

        hilos[0] = new Thread(new MiTarea("A", 1));
        hilos[1] = new Thread(new MiTarea("B", 2));
        hilos[2] = new Thread(new MiTarea("C", 3));

        for (Thread t : hilos) t.start();
        for (Thread t : hilos) t.join();

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo total: " + (fin - inicio) + "ms");
    }
}
