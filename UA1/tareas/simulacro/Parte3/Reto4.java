class Tarea implements Runnable {
    private int segundos;

    public Tarea(int segundos) {
        this.segundos = segundos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(segundos * 1000L);
            System.out.println(
                Thread.currentThread().getName() + " terminado");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Reto4 {
    public static void main(String[] args) throws Exception {
        Thread[] hilos = {
            new Thread(new Tarea(1), "Hilo 1"),
            new Thread(new Tarea(2), "Hilo 2"),
            new Thread(new Tarea(3), "Hilo 3")
        };

        long inicio = System.currentTimeMillis();

        for (Thread h : hilos) {
            h.start();
        }

        for (Thread h : hilos) {
            h.join();
        }

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo total: " + (fin - inicio) + " ms");
    }
}