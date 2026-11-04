import java.io.*;
import java.util.concurrent.TimeUnit;

public class Planificador {
    public static void main(String[] args) {
        String[] nombres = { "A", "B", "C", "falla", "D" };
        String[] segundos = { "2", "1", "8", "1", "-1" };
        Process[] procesos = new Process[nombres.length];

        // contadores para el resumen
        int ok = 0;
        int error = 0;
        int timeout = 0;

        // se lanzan los procesos
        for (int i = 0; i < nombres.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea",
                        nombres[i], segundos[i]);

                // la salida de error va al fichero
                pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores.log")));

                // se ejecuta el proceso
                procesos[i] = pb.start();
            } catch (IOException e) {
                System.out.println("No se pudo lanzar la tarea " + nombres[i] + ": " + e.getMessage());
            }

            // escritura  envia el mensaje al hijo
                 PrintWriter pw = new PrintWriter(procesos[i].getOutputStream(), true);
            pw.println("Hola desde el padre a " + nombres[i]);
            pw.close();
        }
        // se comprueba cada proceso
        for (int i = 0; i < nombres.length; i++) {
            if (procesos[i] == null)
                continue;
            try {
                // espera del proceso
                 if (!procesos[i].waitFor(3, TimeUnit.SECONDS)) {
                    System.out.println("Tarea " + nombres[i] + ": TIMEOUT (cancelada)");
                    timeout++;
                    continue;
                }



                //COMPROBACION DE ERROR
                int codigo = procesos[i].exitValue();
                if (codigo == 0) {
                    System.out.println("Tarea " + nombres[i] + ": OK (codigo " + codigo + ")");
                    ok++;
                } else {
                    System.out.println("Tarea " + nombres[i] + ": ERROR (codigo " + codigo + ")");
                    error++;
                }
                // lectura  obtiene la salida
                BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()));
                String linea;
                    while ((linea = br.readLine()) != null)
                    System.out.println("   " + linea);
            } catch (IOException e) {
                System.out.println("Error al leer la salida de " + nombres[i] + ": " + e.getMessage());
            } catch (InterruptedException e) {
                    System.out.println("Espera interrumpida en " + nombres[i] + ": " + e.getMessage());
            }
        }
        System.out.println("Resumen: " + ok + " OK, " + error + " ERROR, " + timeout + " TIMEOUT");
    }
}