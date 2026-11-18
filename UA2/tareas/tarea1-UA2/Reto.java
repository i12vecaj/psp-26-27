public class Reto implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void main() throws InterruptedException {
        Thread th1 = new Thread(new Reto(),"Hilo 1");
        Thread th2 = new Thread(new Reto(),"Hilo 2");
        Thread th3 = new Thread(new Reto(),"Hilo 3");

        th1.start();
        th1.join();
        th2.start();
        th2.join();
        th3.start();
        th3.join();


    }
}