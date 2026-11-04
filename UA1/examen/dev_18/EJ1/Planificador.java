import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Planificador {
    public static void main(String[] args) {
        String[] nombres = {"A", "B", "C", "falla", "D"};
        String[] segundos = {"2", "1", "8", "1", "-1"};

        List<Process> procesos = new ArrayList<>();

        // Lanza todas las tareas a la vez, sin esperar
        for (int i = 0; i < nombres.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp",
                        System.getProperty("java.class.path"), "Tarea",
                        nombres[i], segundos[i]);
                // La salida de error
                pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores.log")));
                Process proceso = pb.start();
                procesos.add(proceso);

                // Enviar mensaje al hijo
                try {
                    OutputStream salida = proceso.getOutputStream();
                    salida.write(("Hola desde el padre a " + nombres[i] + "\n").getBytes());
                    salida.flush();
                    salida.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (Exception e) {
                System.out.println("Error lanzando " + nombres[i] + ": " + e.getMessage());
                procesos.add(null);
            }
        }

        int totalOk = 0, totalError = 0, totalTimeout = 0;

        //Esperar a las tareas como maximo de 3 segundos
        for (int i = 0; i < procesos.size(); i++) {
            Process proceso = procesos.get(i);
            if (proceso == null) {
                totalError++;
                continue;
            }
            try {
                boolean terminada = proceso.waitFor(3, TimeUnit.SECONDS);

                if (!terminada) {
                    // Cancelar la tarea y no mostrar su salida
                    proceso.destroyForcibly();
                    System.out.println("Tarea " + nombres[i] + ": TIMEOUT (cancelada)");
                    totalTimeout++;
                } else {
                    int codigo = proceso.exitValue();
                    if (codigo == 0) {
                        System.out.println("Tarea " + nombres[i] + ": OK (codigo 0)");
                        totalOk++;
                    } else {
                        System.out.println("Tarea " + nombres[i] + ": ERROR (codigo " + codigo + ")");
                        totalError++;
                    }
                    // Mostrar la salida normal de la tarea
                    BufferedReader lector = new BufferedReader(
                            new InputStreamReader(proceso.getInputStream()));
                    String linea;
                    while ((linea = lector.readLine()) != null) {
                        System.out.println("   " + linea);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error con la tarea " + nombres[i] + ": " + e.getMessage());
            }
        }

        // Resumen de lo anterior
        System.out.println("Resumen: " + totalOk + " OK, " + totalError + " ERROR, "
                + totalTimeout + " TIMEOUT");
    }
}