public class Practica1Parte1 {
    static class Contar implements Runnable{
        private int  contador = 0 ;
        public synchronized void run() {
            try {
                contador += 1000;
                System.out.println(Thread.currentThread().getName()
                        + ": El contador va por " + contador);
                Thread.sleep(100);   // dentro de synchronized: los demás hilos esperan
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    static void main() throws InterruptedException {
        Contar tarea = new Contar();

        Thread t1 = new Thread(tarea, "contar 1");
        Thread t2 = new Thread(tarea, "contar 2");
        Thread t3 = new Thread(tarea, "contar 3");
        Thread t4 = new Thread(tarea, "contar 4");
        Thread t5 = new Thread(tarea, "contar 5");
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
        t4.start();
        t4.join();
        t5.start();
        t5.join();
    }
    }

