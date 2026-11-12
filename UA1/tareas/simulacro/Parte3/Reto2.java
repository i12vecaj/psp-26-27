import java.io.*;
import java.util.concurrent.TimeUnit;

public class Reto2 {
    public static void main(String[] args) throws Exception {
        Process p = new ProcessBuilder(
            "java", "-cp",
            System.getProperty("java.class.path"),
            "Eco"
        ).start();

        PrintWriter escritor = new PrintWriter(
            p.getOutputStream(), true);

        BufferedReader lector = new BufferedReader(
            new InputStreamReader(p.getInputStream()));

        escritor.println("hola");
        escritor.println("mundo");
        escritor.println("fin");
        escritor.close();

        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
        }

        int codigo = p.waitFor();
        System.out.println("Código: " + codigo);

        lector.close();
    }
}