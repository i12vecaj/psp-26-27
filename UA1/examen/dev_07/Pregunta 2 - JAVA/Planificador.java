import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Planificador {

    public static void main(String[] args) {

        int ok = 0;
        int error = 0;
        int fueratiempo = 0;

        try {
            String[][] datos = {
                    {"A", "2"},
                    {"B", "1"},
                    {"C", "8"},
                    {"falla", "1"},
                    {"D", "-1"}

            };

            Process[] procesos = new Process[5];

            //se lanzan todas las tareas
            for (int i = 0; i < procesos.length; i++) {
                ProcessBuilder pb = new ProcessBuilder("java","-cp",System.getProperty("java.class.path"),
                         "Tarea", datos[i][0], datos[i][1]);

                procesos[i] = pb.start();

                PrintWriter pw = new PrintWriter(procesos[i].getOutputStream());
                pw.println("Hola desde padre a: " + datos[i][0]);
                pw.close();
            }

            //esperar 3 seg como maximo
            for (int i = 0; i < datos.length; i++) {
                boolean terminado = procesos[i].waitFor(3, TimeUnit.SECONDS);

                if (!terminado) {
                    procesos[i].destroyForcibly();

                    System.out.println("Tarea " + datos[i][0] + " cancelada (tiempo fuera).");

                    fueratiempo++;

                } else  {
                    int codigo = procesos[i].exitValue();

                    if (codigo == 0) {
                        System.out.println("Tarea " + datos[i][0] + " OK.");
                        ok++;

                    } else {
                        System.out.println("Tarea " + datos[i][0] + " ERROR.");
                        error++;

                    }

                }

            }

            System.out.println(ok + " OK, " +  error + " ERROR." + fueratiempo + " TIMEOUT ");


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
