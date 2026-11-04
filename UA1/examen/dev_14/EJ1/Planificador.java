// Examen UA1 EJ1 Planificador - Federico Lora

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class Planificador {

    public static void main(String[] args) {
        // las tareas
        String[][] tareas = {{"A", "2"}, {"B", "1"}, {"C", "8"}, {"falla", "1"}, {"D", "-1"}};
        Process[] procesos = new Process[tareas.length];
        File errores = new File("errores.log");
        String classpath = System.getProperty("java.class.path");

        // lanza todas a la vez
        for (int i = 0; i < tareas.length; i++) {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, "Tarea", tareas[i][0], tareas[i][1]);
            pb.redirectError(ProcessBuilder.Redirect.appendTo(errores));
            try {
                procesos[i] = pb.start();
            } catch (IOException e) {
                System.err.println("No se pudo lanzar la tarea " + tareas[i][0]);
            }
        }

        // manda el mensaje a cada una
        for (int i = 0; i < procesos.length; i++) {
            if (procesos[i] == null) {
                continue;
            }
            try {
                OutputStream os = procesos[i].getOutputStream();
                os.write(("Hola desde el padre a " + tareas[i][0] + "\n").getBytes());
                os.flush();
                os.close();
            } catch (IOException e) {
                System.err.println("No se pudo mandar el mensaje a " + tareas[i][0]);
            }
        }

        int ok = 0;
        int error = 0;
        int timeout = 0;

        // espera 3 segundos a cada una
        for (int i = 0; i < procesos.length; i++) {
            if (procesos[i] == null) {
                continue;
            }
            String nombre = tareas[i][0];
            try {
                // si tarda mucho se cancela
                if (!procesos[i].waitFor(3, TimeUnit.SECONDS)) {
                    procesos[i].destroy();
                    System.out.println("Tarea " + nombre + ": TIMEOUT (cancelada)");
                    timeout++;
                    continue;
                }
                int codigo = procesos[i].exitValue();
                if (codigo == 0) {
                    System.out.println("Tarea " + nombre + ": OK (codigo 0)");
                    ok++;
                } else {
                    System.out.println("Tarea " + nombre + ": ERROR (codigo " + codigo + ")");
                    error++;
                }
                mostrarSalida(procesos[i]);
            } catch (InterruptedException e) {
                System.err.println("Se corto la espera de " + nombre);
            }
        }

        // resumen
        System.out.println("Resumen: " + ok + " OK, " + error + " ERROR, " + timeout + " TIMEOUT");
    }

    // enseña lo que escribe la tarea
    static void mostrarSalida(Process p) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("   " + linea);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("No se pudo leer la salida");
        }
    }
}
