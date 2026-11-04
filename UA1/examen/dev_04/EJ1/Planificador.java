package EJ1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Planificador {
    public static void main(String[] args) {
        String[][] tareas = {{"A", "2"}, {"B", "1"}, {"C", "8"}, {"falla", "1"}, {"D", "-1"}};
        Process[] procesos = new Process[tareas.length];

        try {
            // Primero se lanzan todas
            String path = System.getProperty("java.class.path");
            for (int i = 0; i < procesos.length; i++) {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", path, "EJ1.Tarea", tareas[i][0], tareas[i][1]);
                pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores.log")));
                procesos[i] = pb.start();
            }


            for (int i = 0; i < procesos.length; i++) {
                boolean terminada = procesos[i].waitFor(3, TimeUnit.SECONDS);

                if (terminada) {
                    int codigo = procesos[i].exitValue();
                    System.out.println("Tarea " + tareas[i][0] + ": " + estado(codigo));
                    leerSalida(procesos[i]);
                } else {
                    // Si no ha terminado se cancela
                    procesos[i].waitFor();
                    System.out.println("Tarea " + tareas[i][0] + ": cancelada");
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al lanzar o leer una tarea: " + e.getMessage());
        }
    }

    // Lee linea y linea y lo muestra
    static void leerSalida(Process tarea) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(tarea.getInputStream()));
        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
        }
    }

    // Devuelve el estado
    static String estado(int codigo) {
        if (codigo == 0) {
            return "OK (codigo 0)";
        }
        return "ERROR (codigo " + codigo + ")";
    }
}






