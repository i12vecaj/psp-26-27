public class FR1 implements Runnable{

    int contable =0;
    static void main(String[] args) throws InterruptedException {
        FR1 reto = new FR1();

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
        System.out.println("Finalizacion");

    }

    @Override
    public void run() {
        contable += 1000;
        System.out.println(Thread.currentThread().getName()+" |sumita "+contable);
    }
}
