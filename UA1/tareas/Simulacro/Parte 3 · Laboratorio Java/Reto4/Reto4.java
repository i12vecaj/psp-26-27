class Durmiente implements Runnable {
    private int segundos;

    // HUECO 1: constructor que reciba los segundos y los guarde
    public Durmiente(int segundos) {
        this.segundos = segundos;
    }

    public void run() {
        try {
            //Thread.sleep va en milisegundos
            Thread.sleep(segundos * 1000);

            System.out.println(Thread.currentThread().getName() + " ha acabado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Reto4 {
    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();

        Thread[] hilos = new Thread[3];

        /* 
        // primer bucle
        //          hilos[i] = new Thread(new Durmiente(i + 1), "Hilo-" + (i + 1));
        for (int i = 0; i < 3; i++) {
            hilos[i] = new Thread(new Durmiente(i + 1), "Hilo-" + (i + 1));
            hilos[i].start();
        }

        // segundo bucle
        for (int i = 0; i < 3; i++) {
            hilos[i].join();
        }
            */

        //Join justo despues del start
        
        for (int i = 0; i < 3; i++) {
            hilos[i] = new Thread(new Durmiente(i + 1), "Hilo-" + (i + 1));
            hilos[i].start();
            hilos[i].join();
        }
        long fin = System.currentTimeMillis();
        System.out.println("Total: " + (fin - inicio) + " ms");
    }
}

