    static class Reto implements Runnable {
        @Override
        public void run() {
            {
                int contador = 0;
                while (contador < 5) {
                    contador++;
                    try {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static void main() throws InterruptedException {
        Thread t1 = new Thread(new Reto(),"Reto 1");
        Thread t2 = new Thread(new Reto(),"Reto 2");
        Thread t3 = new Thread(new Reto(),"Reto 3");
        Thread t4 = new Thread(new Reto(),"Reto 4");
        Thread t5 = new Thread(new Reto(),"Reto 5");
    t1.start();t1.join();
    t2.start();t2.join();
    t3.start();t3.join();
    t4.start();t4.join();
    t5.start();t5.join();
    }

