import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniMonitor {
    static void main() throws IOException, InterruptedException {
        // 1. TODO: crea el ProcessBuilder con el comando 2. TODO: lanza el proceso con start()
        Process p = new ProcessBuilder().start();
        // 3. TODO: muestra el PID
        System.out.println("PID: " + p.pid());
        // 4. TODO: lee la salida línea a línea (BufferedReader + InputStreamReader)
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while (r.readLine() != null);
        // 5. TODO: espera a que termine con waitFor()
        int code = p.waitFor();
        // 6. TODO: muestra líneas leídas y código de salida (0 = correcto)
        System.out.println("Líneas leídas y código " + code + (code == 0 ? " (Correcto)" : " (Falló)"));
    }
}
