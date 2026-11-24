
public class Practica1Parte3 {
    static class Contador {
        private int contador = 0;

        public synchronized int incrementar(int cantidad) {
            contador += cantidad;
            return contador;
        }
        public synchronized int getContador() {
            return contador;
        }
    }
    static class HiloContador extends Thread {
        private final Contador compartido;

        HiloContador(Contador compartido, String nombre) {
            super(nombre);
            this.compartido = compartido;
        }


        @Override
        public void run() {
            try {
                int valor = compartido.incrementar(1000);   // sección crítica breve
                System.out.println(getName() + ": El contador va por " + valor);
                Thread.sleep(100);                          // fuera de synchronized
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(getName() + " interrumpido");
            }
        }
    }



    public static void main(String[] args) {
        final int n_Hilos = 5;
        final int e_hilos = n_Hilos * 1000;

        Contador compartido = new Contador();
        HiloContador[] hilos = new HiloContador[n_Hilos];

        for (int i = 0; i < n_Hilos; i++) {
            hilos[i] = new HiloContador(compartido, "contar " + (i + 1));
            hilos[i].start();
        }
        try {
            for (HiloContador hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo principal fue interrumpido mientras esperaba");
            return;
        }
        int obtenido = compartido.getContador();
        System.out.println("\nResultado esperado: " + e_hilos);
        System.out.println("Resultado obtenido: " + obtenido);
        System.out.println(obtenido == e_hilos
                ? "La sincronización evita la condición de carrera."
                : "El resultado no coincide.");
    }
}
