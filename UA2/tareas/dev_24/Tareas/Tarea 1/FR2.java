public class FR2 implements Runnable    {

    int contable =0;
    static void main(String[] args) throws InterruptedException {
        FR2 reto = new FR2();

        Thread hilo1 = new Thread(reto,"hilito1");
        Thread hilo2 = new Thread(reto,"Hilito2");
        Thread hilo3 = new Thread(reto,"Hilito3");
        Thread hilo4 = new Thread(reto,"Hilito4");
        Thread hilo5 = new Thread(reto,"Hilito5");
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        hilo1.join();
        hilo2.join();
        hilo3.join();
        hilo4.join();
        hilo5.join();
        System.out.println("Finalizacion");

    }

    private synchronized void incrementar(){
        contable ++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            incrementar();
        }
        System.out.println(Thread.currentThread().getName()+" |Ha terminado ");
    }
}
