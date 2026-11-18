/*
public class Tarea1UA2 implements Runnable{

    int contador = 0;
    @Override
    public void run() {
        contador += 1000;
        System.out.println("Contador: " + contador);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static void main() throws InterruptedException {
        Thread th1 = new Thread(new Tarea1UA2(),"Hilo 1");
        Thread th2 = new Thread(new Tarea1UA2(),"Hilo 2");
        Thread th3 = new Thread(new Tarea1UA2(),"Hilo 3");
        Thread th4 = new Thread(new Tarea1UA2(),"Hilo 4");
        Thread th5 = new Thread(new Tarea1UA2(),"Hilo 5");
        th1.start();

        th2.start();

        th3.start();

        th4.start();

        th5.start();
        th5.join();
        th1.join();
        th2.join();
        th3.join();
        th4.join();
    }


    RESULTADO FR1:
        Contador: 1000
        Contador: 1000
        Contador: 1000
        Contador: 1000
        Contador: 1000

    No se obtiene el resultado esperado.
    Yo esperaba que el contador se pusiese en 5000 ya que los 5 hilos acceden a la misma variable



}*/

class TareaThread extends Thread {
    private static int contador = 0;

    public TareaThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (TareaThread.class) {
            contador += 1000;
            System.out.println(getName() + " - Contador: " + contador);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getContadorFinal() {
        return contador;
    }
}

class TareaRunnable implements Runnable {
    private int contador = 0;

    @Override
    public void run() {
        synchronized (this) {
            contador += 1000;
            System.out.println(Thread.currentThread().getName() + " - Contador: " + contador);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getContadorFinal() {
        return contador;
    }
}

// Clase Principal
public class Tarea1UA2 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Usando Clase thread");
        Thread t1 = new TareaThread("Thread 1");
        Thread t2 = new TareaThread("Thread 2");
        Thread t3 = new TareaThread("Thread 3");
        Thread t4 = new TareaThread("Thread 4");
        Thread t5 = new TareaThread("Thread 5");

        t1.start(); t2.start(); t3.start(); t4.start(); t5.start();

        t1.join(); t2.join(); t3.join(); t4.join(); t5.join();

        System.out.println("Resultado final: " + TareaThread.getContadorFinal() + "\n");


        System.out.println("Usando Runnable");
        TareaRunnable tareaCompartida = new TareaRunnable();

        Thread r1 = new Thread(tareaCompartida, "Runnable 1");
        Thread r2 = new Thread(tareaCompartida, "Runnable 2");
        Thread r3 = new Thread(tareaCompartida, "Runnable 3");
        Thread r4 = new Thread(tareaCompartida, "Runnable 4");
        Thread r5 = new Thread(tareaCompartida, "Runnable 5");

        r1.start(); r2.start(); r3.start(); r4.start(); r5.start();

        r1.join(); r2.join(); r3.join(); r4.join(); r5.join();

        System.out.println("Resultado final: " + tareaCompartida.getContadorFinal());
    }
}

