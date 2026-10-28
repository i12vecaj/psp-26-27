import java.io.*;
import java.util.concurrent.TimeUnit;

public class Reto2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String cp = System.getProperty("java.class.path");

        // le mandamos hola, mundo y fin a Eco
        Process p = new ProcessBuilder("java", "-cp", cp, "Eco").start();
        PrintWriter pw = new PrintWriter(p.getOutputStream(), true);
        pw.println("hola");
        pw.println("mundo");
        pw.println("fin");
        pw.close();

        // leemos lo que responde
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println("Eco responde: " + linea);
        }
        System.out.println("Codigo de salida: " + p.waitFor());

        // ampliacion: otro Eco al que no le mandamos fin ni cerramos el flujo
        Process p2 = new ProcessBuilder("java", "-cp", cp, "Eco").start();
        PrintWriter pw2 = new PrintWriter(p2.getOutputStream(), true);
        pw2.println("hola");
        pw2.println("mundo");

        if (p2.waitFor(3, TimeUnit.SECONDS)) {
            System.out.println("El segundo Eco ha terminado solo");
        } else {
            System.out.println("El segundo Eco no ha terminado en 3 segundos, lo destruyo");
            p2.destroy();
        }
    }
}
