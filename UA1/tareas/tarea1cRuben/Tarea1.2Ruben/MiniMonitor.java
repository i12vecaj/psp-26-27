
import java.io.*;

public class MiniMonitor {

    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {

        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                : new String[]{"ls", "-l"};

        // 1. Preparamos el comando
        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2. Lanzamos el proceso
        Process p = pb.start();

        // 3. Mostramos el PID
        System.out.println("PID del proceso: " + p.pid());

        // 4. Leemos salida con  getInputStream
        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));

        String linea;
        int contador = 0;

        while ((linea = br.readLine()) != null) {
            contador++;
            System.out.println(contador + ": " + linea);
        }

        br.close();

        // 5. Esperar a que termine
        int codigoSalida = p.waitFor();

        // 6.  informe final
        System.out.println("----- INFORME FINAL -----");
        System.out.println("Lineas leidas: " + contador);
        System.out.println("Codigo de salida: " + codigoSalida);

        if (codigoSalida == 0) {
            System.out.println("Proceso ejecutado correctamente");
        } else {
            System.out.println("El proceso ha fallado");
        }
    }
}
