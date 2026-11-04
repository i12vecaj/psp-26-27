import java.io.IOException;

public class Reto3 {

    public static void main(String[] args) throws Exception {
        // Dos hijos secuenciales: aproximadamente 4 segundos.
        long inicio = System.currentTimeMillis();

        Process primero = lanzarHijo(2);
        primero.waitFor();

        Process segundo = lanzarHijo(2);
        segundo.waitFor();

        System.out.println(
                "Secuencial: "
                        + (System.currentTimeMillis() - inicio) + " ms"
        );

        // Dos hijos en paralelo: aproximadamente 2 segundos.
        inicio = System.currentTimeMillis();

        primero = lanzarHijo(2);
        segundo = lanzarHijo(2);

        primero.waitFor();
        segundo.waitFor();

        System.out.println(
                "Paralelo: "
                        + (System.currentTimeMillis() - inicio) + " ms"
        );

        // Ampliación: tres hijos en paralelo, unos 2 segundos.
        inicio = System.currentTimeMillis();
        Process[] hijos = new Process[3];

        for (int i = 0; i < hijos.length; i++) {
            hijos[i] = lanzarHijo(2);
        }

        for (Process hijo : hijos) {
            hijo.waitFor();
        }

        System.out.println(
                "Tres en paralelo: "
                        + (System.currentTimeMillis() - inicio) + " ms"
        );
    }

    private static Process lanzarHijo(int segundos) throws IOException {
        return new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                Espera.class.getName(),
                String.valueOf(segundos)
        ).inheritIO().start();
    }

    public static class Espera {
        public static void main(String[] args) {
            if (args.length == 0) {
                System.err.println("Debes indicar los segundos.");
                System.exit(1);
                return;
            }

            try {
                int segundos = Integer.parseInt(args[0]);

                if (segundos < 0) {
                    System.err.println("Los segundos no pueden ser negativos.");
                    System.exit(2);
                    return;
                }

                Thread.sleep(segundos * 1000L);

            } catch (NumberFormatException e) {
                System.err.println("Introduce un número entero.");
                System.exit(3);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Espera interrumpida.");
                System.exit(4);
            }
        }
    }
}