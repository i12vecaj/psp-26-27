package UA2.Tarea3;


public class ContadorCaracteres {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Error: debes indicar al menos un fichero.");
            System.out.println("Ejemplo: java ContadorCaracteres fichero1.txt fichero2.txt");
            return;
        }

        System.out.println("===== EJECUCIÓN SECUENCIAL =====");

        long t_comienzo = System.currentTimeMillis();

        ContadorSecuencial.ejecutar(args);

        long t_fin = System.currentTimeMillis();

        long tiempoSecuencial = t_fin - t_comienzo;

        System.out.println("Tiempo secuencial: "
                + tiempoSecuencial + " ms");

        System.out.println("\n===== EJECUCIÓN CONCURRENTE =====");

        t_comienzo = System.currentTimeMillis();

        ContadorConcurrente.ejecutar(args);

        t_fin = System.currentTimeMillis();

        long tiempoConcurrente = t_fin - t_comienzo;

        System.out.println("Tiempo concurrente: "
                + tiempoConcurrente + " ms");

        System.out.println("\n===== COMPARACIÓN =====");

        System.out.println("Tiempo secuencial: "
                + tiempoSecuencial + " ms");

        System.out.println("Tiempo concurrente: "
                + tiempoConcurrente + " ms");

        if (tiempoSecuencial > tiempoConcurrente) {

            System.out.println("La ejecución concurrente ha sido más rápida.");

        } else if (tiempoSecuencial < tiempoConcurrente) {

            System.out.println("La ejecución secuencial ha sido más rápida.");

        } else {

            System.out.println("Ambas ejecuciones han tardado lo mismo.");
        }
    }
}

