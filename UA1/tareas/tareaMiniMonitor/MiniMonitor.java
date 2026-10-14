
import java.io.*;

public class MiniMonitor {

    static final boolean WIN = System.getProperty("os.name")
            .toLowerCase().contains("win");

    public static void main(String[] args)
            throws IOException, InterruptedException {

        String[] comando = WIN
                ? new String[]{"cmd", "/c", "dir"}
                : new String[]{"ls", "-l"};

        // 1. Preparar el comando con ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2. Lanzar el proceso con start()
        Process proceso = pb.start();

        // 3. Mostrar el PID del proceso hijo
        System.out.println("PID del proceso: " + proceso.pid());

        // 4. Leer la salida línea a línea y numerarla
        int lineas = 0;

        BufferedReader lector = new BufferedReader(
                new InputStreamReader(proceso.getInputStream())
        );

        String linea;

        while ((linea = lector.readLine()) != null) {
            lineas++;
            System.out.println(lineas + ": " + linea);
        }

        lector.close();

        // 5. Esperar a que termine el proceso
        int codigoSalida = proceso.waitFor();

        // 6. Mostrar el informe final
        System.out.println("\n===== INFORME FINAL =====");
        System.out.println("Líneas leídas: " + lineas);
        System.out.println("Código de salida: " + codigoSalida);

        if (codigoSalida == 0) {
            System.out.println("Resultado: CORRECTO");
        } else {
            System.out.println("Resultado: FALLO");
        }
    }
}
