import java.io.*;

public class Reto2 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            "java", "-cp", System.getProperty("java.class.path"), "Eco");
        Process p = pb.start();

        PrintWriter escritor = new PrintWriter(p.getOutputStream(), true);
        BufferedReader lector = new BufferedReader(new InputStreamReader(p.getInputStream()));

        escritor.println("hola");
        escritor.println("mundo");
        escritor.println("fin");
        escritor.close();

        String linea;
        while ((linea = lector.readLine()) != null) System.out.println(linea);

        p.waitFor();
    }
}