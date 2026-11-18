package prestalope.ui;

public class Reto3 {

static class nombreHilo implements Runnable{
    @Override
    public void run() {
        int vueltas = 0;
        while (!Thread.currentThread().isInterrupted()) {
            vueltas++;
            System.out.println("Nombre del hilo "+ Thread.currentThread().getName()+ " "+ vueltas+" veces");
            try {
                Thread.sleep(100);
                if(vueltas==5){
                    Thread.currentThread().interrupt();
            }
            } catch (InterruptedException e) {
                System.err.println("Algo ha fallao");         }
        }
    }

    static void main() throws InterruptedException {
        Thread hilo1 = new Thread(new Reto3.nombreHilo(), "hilo-1");
        Thread hilo2 = new Thread(new Reto3.nombreHilo(), "hilo-2");
        Thread hilo3 = new Thread(new Reto3.nombreHilo(), "hilo-3");
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo1.join();
        hilo2.join();
        hilo3.join();




    }
}

}
