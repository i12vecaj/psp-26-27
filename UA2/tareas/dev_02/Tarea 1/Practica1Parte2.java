public class Practica1Parte2 {

    static class Contar implements Runnable {
        private int contador = 0;
        @Override
        public void run() {
            try {
                int leido = contador;
                Thread.sleep(10);
                contador = leido + 1000;
                System.out.println(Thread.currentThread().getName()
                        + ": El contador va por " + contador);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(Thread.currentThread().getName() + " interrumpido");
            }
        }
        public int getContador() {
            return contador;
        }
    }
    public static void main(String[] args) {
        final int nHilos = 5;
        final int esperaHilos = nHilos * 1000;

        Contar tarea = new Contar();
        Thread[] hilos = new Thread[nHilos];

         for (int i = 0; i < nHilos; i++) {
            hilos[i] = new Thread(tarea, "contar " + (i + 1));
            hilos[i].start();
        }



        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo principal fue interrumpido mientras esperaba");
            return;
        }
        int obtenido = tarea.getContador();
        System.out.println("\nResultado esperado: " + esperaHilos);
        System.out.println("Resultado obtenido: " + obtenido);
        if (obtenido != esperaHilos) {
            System.out.println("(se han perdido " + (esperaHilos - obtenido) + " unidades).");
        } else {
            System.out.println("la carrera sigue existiendo.");
        }
    }
}
