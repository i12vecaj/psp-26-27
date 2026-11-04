
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;

public class Planificador {

    public static void main(String[] args) {

        // guardamos el nombre y los segundos de cada tarea
        String[][] tareas = {
                {"A", "2"},
                {"B", "1"},
                {"C", "8"},
                {"falla", "1"},
                {"D", "-1"}
        };

        ArrayList<Process> procesos = new ArrayList<>();
        ArrayList<String> nombres = new ArrayList<>();

        // variables para contar los resultados
        int ok = 0;
        int error = 0;
        int timeout = 0;

        try {
            // iniciamos los cinco procesos
            for (String[] tarea : tareas) {

                String nombre = tarea[0];
                String segundos = tarea[1];

                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "-cp",
                        System.getProperty("java.class.path"),
                        "Tarea",
                        nombre,
                        segundos
                );

                // guardamos los errores en errores.log sin sobrescribirlo
                pb.redirectError(
                        ProcessBuilder.Redirect.appendTo(new File("errores.log"))
                );

                Process proceso = pb.start();

                procesos.add(proceso);
                nombres.add(nombre);

                // enviamos el mensaje al proceso hijo
                BufferedWriter escritor = new BufferedWriter(
                        new OutputStreamWriter(proceso.getOutputStream())
                );

                escritor.write("Hola desde el padre a " + nombre);
                escritor.newLine();
                escritor.close();
            }

            // esperamos a cada proceso un máximo de tres segundos
            for (int i = 0; i < procesos.size(); i++) {

                Process proceso = procesos.get(i);
                String nombre = nombres.get(i);

                if (proceso.waitFor(3, java.util.concurrent.TimeUnit.SECONDS)) {

                    // comprobamos el código de finalización
                    int codigo = proceso.exitValue();

                    if (codigo == 0) {
                        System.out.println("Tarea " + nombre + ": OK (codigo 0)");
                        ok++;
                    } else {
                        System.out.println("Tarea " + nombre + ": ERROR (codigo " + codigo + ")");
                        error++;
                    }

                    // leemos la salida normal del proceso
                    BufferedReader lector = new BufferedReader(
                            new InputStreamReader(proceso.getInputStream())
                    );

                    String linea;

                    while ((linea = lector.readLine()) != null) {
                        System.out.println("   " + linea);
                    }

                    lector.close();

                } else {

                    // si tarda más de tres segundos, cancelamos el proceso
                    proceso.destroyForcibly();

                    System.out.println("Tarea " + nombre + ": TIMEOUT (cancelada)");
                    timeout++;
                }
            }

            // mostramos el resumen de todas las tareas
            System.out.println("Resumen: " + ok + " OK, "
                    + error + " ERROR, " + timeout + " TIMEOUT");

        } catch (Exception e) {
            System.err.println("Error en el planificador: " + e.getMessage());
        }
    }
}
