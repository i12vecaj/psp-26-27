package prestalope.ui;

public class Practica1 {
    private static int contador = 0;
    private static int turno = 0;
    private static final Object cerrojo = new Object();

    static class SumaHilo implements Runnable {
        private int id;

        SumaHilo(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            synchronized (cerrojo) {
                while (turno != id) {
                    cerrojo.notifyAll();
                    try {
                        cerrojo.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);

                    }
                }
            }
            sumar();
            System.out.println("Numero: " + contador);
        }
    }

    static void main() throws InterruptedException {
        System.out.println("Numero: " + contador);
        Thread hilo1 = new Thread(new SumaHilo(), "hilo-1");
        Thread hilo2 = new Thread(new SumaHilo(), "hilo-2");
        Thread hilo3 = new Thread(new SumaHilo(), "hilo-3");
        Thread hilo4 = new Thread(new SumaHilo(), "hilo-4");
        Thread hilo5 = new Thread(new SumaHilo(), "hilo-5");


        /**
         * FR1:
         *No obtengo el resultado esperado ya que se hacen en diferentes ordenes
         * y sin ningun tipo de control y creo que por pura suerte no han actualizado
         * el numero 2 hilos simultaneamente sumando solo 1000 entre los dos
         *  Resultado:
         *  Numero: 0
         * Numero: 1000
         * Numero: 2000
         * Numero: 5000
         * Numero: 4000
         * Numero: 3000
         * */

    }

    static void sumar() {
        contador += 1000;
    }
}


