import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Planificador {
    public static void main(String[] args) {

        String[][] Tareas = {{"A", "2"}, {"B", "1"}, {"C", "8"}, {"falla", "1"}, {"D", "-1"}};
        Process[] procesos = new Process[Tareas.length];
        String cp = System.getProperty("java.class.path");
        for (int i = 0; i < Tareas.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, "Tarea", Tareas[i][0], Tareas[i][1]);
                pb.redirectError(ProcessBuilder.Redirect.appendTo(new java.io.File("Errores.log")));
                procesos[i] = pb.start();
                procesos[i].getOutputStream();
                System.out.println("Hola desde padre a "+ Tareas[i][0]);
            } catch (Exception e) {
                System.out.println("Error al lanzar el hijo con arg: " + Tareas[i][0]);
            }
        }
        int sumaTotal = 0;

        // FR3 y FR4: Leemos la salida y esperamos a cada proceso
        for (int i = 0; i < procesos.length; i++) {
            int numHijo = i + 1;
            String arg = Arrays.toString(Tareas[i]);
            int sumaHijo = 0;

            try {
                // Leer lo que escribe el hijo
                BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    sumaHijo += Integer.parseInt(linea);
                }
                br.close();

                // Esperar a que acabe para pillar el código de salida
                procesos[i].waitFor(3, TimeUnit.SECONDS);

            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }
}