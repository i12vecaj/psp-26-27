/** Las prioridades son una sugerencia, no una garantía. 10 = máxima, 1 = mínima. */
public class Demo05_Prioridades {

    static volatile boolean fin = false;

    static class Contador extends Thread {
        long vueltas = 0;

        @Override
        public void run() {
            while (!fin) vueltas++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Contador baja = new Contador();
        Contador alta = new Contador();
        baja.setPriority(Thread.MIN_PRIORITY);  // 1
        alta.setPriority(Thread.MAX_PRIORITY);  // 10

        baja.start();
        alta.start();
        Thread.sleep(1000);
        fin = true;
        baja.join();
        alta.join();

        System.out.println("Vueltas prioridad 1 : " + baja.vueltas);
        System.out.println("Vueltas prioridad 10: " + alta.vueltas);
    }
}
