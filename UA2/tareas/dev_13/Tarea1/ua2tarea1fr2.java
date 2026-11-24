public class ua2tarea1fr2 {
    static int contador = 0;

    static void main(String[] args) {

        Thread thread1 = new Thread (new ua2tarea1fr2runnable());
        Thread thread2 = new Thread (new ua2tarea1fr2runnable());
        Thread thread3 = new Thread (new ua2tarea1fr2runnable());
        Thread thread4 = new Thread (new ua2tarea1fr2runnable());
        Thread thread5 = new Thread (new ua2tarea1fr2runnable());

        thread1.start(); thread2.start(); thread3.start();
        thread4.start(); thread5.start();

    }
}
