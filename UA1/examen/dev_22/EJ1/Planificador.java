
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Planificador {

    public static void main(String[] args) {


        String[] nombres = {"A", "B", "C", "falla", "D"};
        String[] segundos = {"2", "1", "8", "1", "-1"};

        Process[] procesos = new Process[5];

        int ok = 0;
        int error = 0;
        int timeout = 0;

        try {

            for (int i = 0; i < procesos.length; i++) {

                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "-cp",
                        System.getProperty("java.class.path"),
                        "Tarea",
                        nombres[i],
                        segundos[i]
                );

                pb.redirectError(ProcessBuilder.Redirect.appendTo(
                        new File("errores.log")));

                procesos[i] = pb.start();

                OutputStream entrada = procesos[i].getOutputStream();

                String mensaje = "Hola desde el padre a " + nombres[i];

                entrada.write((mensaje + "\n").getBytes());
                entrada.close();
            }

            for (int i = 0; i < procesos.length; i++) {

                Process proceso = procesos[i];

                boolean terminado = proceso.waitFor(3, TimeUnit.SECONDS);

                if (!terminado) {


                    proceso.destroyForcibly();
                    proceso.waitFor();

                    System.out.println("Tarea " + nombres[i]
                            + ": TIMEOUT (cancelada)");

                    timeout++;

                } else {


                    int codigo = proceso.exitValue();

                    if (codigo == 0) {

                        System.out.println("Tarea " + nombres[i]
                                + ": OK (codigo 0)");

                        ok++;

                    } else {

                        System.out.println("Tarea " + nombres[i]
                                + ": ERROR (codigo " + codigo + ")");

                        error++;
                    }


                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(proceso.getInputStream()));

                    String linea;

                    while ((linea = br.readLine()) != null) {
                        System.out.println("   " + linea);
                    }

                    br.close();
                }
            }


            System.out.println("Resumen: " + ok + " OK, "
                    + error + " ERROR, " + timeout + " TIMEOUT");

        } catch (IOException e) {

            System.err.println("Error al crear o leer los procesos");

        } catch (InterruptedException e) {

            System.err.println("El planificador ha sido interrumpido");
            Thread.currentThread().interrupt();
        }
    }
}
