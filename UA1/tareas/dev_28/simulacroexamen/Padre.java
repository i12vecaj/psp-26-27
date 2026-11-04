import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


public class Padre {

    public static void main(String[] args) {
        System.exit(ejecutar());
    }

    private static int ejecutar() {
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] procesos = new Process[argumentos.length];

        // Localiza el ejecutable del mismo Java que utiliza el padre.
        String ejecutable =
                System.getProperty("os.name").startsWith("Windows")
                        ? "java.exe"
                        : "java";

        String rutaJava = Paths.get(
                System.getProperty("java.home"),
                "bin",
                ejecutable
        ).toString();

        try {
            // Primero se lanzan TODOS los hijos, sin esperar a ninguno.
            for (int i = 0; i < argumentos.length; i++) {
                ProcessBuilder constructor = new ProcessBuilder(
                        rutaJava,
                        "-cp",
                        System.getProperty("java.class.path"),
                        "Hijo",
                        argumentos[i]
                );

                // Separa los errores de los números enviados por stdout.
                constructor.redirectError(ProcessBuilder.Redirect.INHERIT);
                procesos[i] = constructor.start();
            }

            long sumaTotal = 0;

            // Los hijos ya están lanzados antes de comenzar las lecturas.
            for (int i = 0; i < procesos.length; i++) {
                long suma = leerSuma(procesos[i]);
                int codigo = procesos[i].waitFor();

                sumaTotal += suma;

                System.out.printf(
                        "Hijo %d (arg=%s): codigo %d -> %s, suma=%d%n",
                        i + 1,
                        argumentos[i],
                        codigo,
                        explicarCodigo(codigo),
                        suma
                );
            }

            System.out.println("Suma total: " + sumaTotal);
            return 0;

        } catch (IOException e) {
            System.err.println(
                    "Error al lanzar o leer un hijo: " + e.getMessage()
            );
            return 1;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(
                    "Padre interrumpido mientras esperaba a un hijo."
            );
            return 1;

        } finally {
            detenerProcesos(procesos);
        }
    }

    /**
     * Lee los números que escribe un hijo y calcula su suma.
     */
    private static long leerSuma(Process proceso) throws IOException {
        long suma = 0;

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(
                        proceso.getInputStream(),
                        StandardCharsets.UTF_8
                )
        )) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                try {
                    suma += Long.parseLong(linea);
                } catch (NumberFormatException e) {
                    throw new IOException(
                            "Salida no numérica del hijo: " + linea,
                            e
                    );
                }
            }
        }

        return suma;
    }

    /**
     * Traduce los códigos de salida a mensajes comprensibles.
     */
    private static String explicarCodigo(int codigo) {
        switch (codigo) {
            case 0:
                return "OK";
            case 1:
                return "falta argumento";
            case 2:
                return "no es un numero";
            case 3:
                return "numero negativo";
            case 4:
                return "ejecucion interrumpida";
            default:
                return "error no previsto";
        }
    }

    /**
     * Evita dejar hijos activos si el padre termina por un error.
     */
    private static void detenerProcesos(Process[] procesos) {
        for (Process proceso : procesos) {
            if (proceso != null && proceso.isAlive()) {
                proceso.destroyForcibly();
            }
        }
    }
}