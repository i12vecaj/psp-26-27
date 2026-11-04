public class reto4 {
    static class Thilos implements Runnable{
        private int segundos;

        public Thilos(int segundos){
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
                System.out.println("hilo terminado " + Thread.currentThread().getName());
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        Thread[] hilos = new Thread[3];
        hilos[0] = new Thread(new Thilos(1), "hilo1");
        hilos[1] = new Thread(new Thilos(2), "hilo2");
        hilos[2] = new Thread(new Thilos(3), "hilo3");

        for (Thread h : hilos){
            h.start();
        }

        for (Thread h : hilos){
            try{
                h.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        long fin = System.currentTimeMillis();
        System.out.println("tiempo total " + (fin + inicio));

    }
}
