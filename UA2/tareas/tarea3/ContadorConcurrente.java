package UA2.Tarea3;


public class ContadorConcurrente {

    public static void ejecutar(String[] ficheros) {

        ContadorHilo[] hilos = new ContadorHilo[ficheros.length];

        for (int i = 0; i < ficheros.length; i++) {

            hilos[i] = new ContadorHilo(ficheros[i]);
            hilos[i].start();
        }

        for (int i = 0; i < hilos.length; i++) {

            try {
                hilos[i].join();

            } catch (InterruptedException e) {
                System.out.println("Se ha interrumpido la espera de los hilos.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

