import java.io.*;
import java.util.concurrent.TimeUnit;

public class Planificador {

    public static void main(String[] args) {

        String[] nombres = {"A", "B", "C", "falla", "D"};
        int[] segundos = {2, 1, 8, 1, -1};

        Process[] procesos = new Process[5];
        long[] inicios = new long[5];

        int ok = 0;
        int error = 0;
        int timeout = 0;

        try {

            // Crear todos los procesos
            for (int i = 0; i < 5; i++) {

                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp",
                        System.getProperty("java.class.path"),
                        "Tarea", nombres[i], "" + segundos[i]
                );

                pb.redirectError(ProcessBuilder.Redirect.appendTo(
                        new File("errores.log")
                ));

                procesos[i] = pb.start();

                // Guardar el momento de inicio
                inicios[i] = System.nanoTime();
            }

            // Enviar mensajes
            for (int i = 0; i < 5; i++) {

                PrintWriter pw = new PrintWriter(
                        procesos[i].getOutputStream()
                );

                pw.println("Hola desde el padre a " + nombres[i]);
                pw.close();
            }

            // Esperar como máximo 3 segundos desde el inicio
            for (int i = 0; i < 5; i++) {

                System.out.print("Tarea " + nombres[i] + ": ");

                long transcurrido = System.nanoTime() - inicios[i];
                long restante = TimeUnit.SECONDS.toNanos(3) - transcurrido;

                boolean terminado = procesos[i].waitFor(
                        Math.max(0, restante), TimeUnit.NANOSECONDS
                );

                if (terminado) {

                    int codigo = procesos[i].exitValue();

                    if (codigo == 0) {
                        System.out.println("OK (codigo 0)");
                        ok++;
                    } else {
                        System.out.println("ERROR (codigo " + codigo + ")");
                        error++;
                    }

                    // Leer salida normal
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(procesos[i].getInputStream())
                    );

                    String linea;

                    while ((linea = br.readLine()) != null) {
                        System.out.println("   " + linea);
                    }

                } else {

                    procesos[i].destroyForcibly();
                    System.out.println("TIMEOUT (cancelada)");
                    timeout++;
                }
            }

            // Mostrar resumen
            System.out.println("Resumen: " + ok + " OK, "
                    + error + " ERROR, " + timeout + " TIMEOUT");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}