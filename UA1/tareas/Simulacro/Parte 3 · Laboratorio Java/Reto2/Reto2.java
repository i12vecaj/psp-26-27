
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Reto2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = System.getProperty("java.class.path");
        Process eco = new ProcessBuilder("java", "-cp", path, "Eco").start();

        PrintWriter escritor = new PrintWriter(eco.getOutputStream(), true);

        escritor.println("hola");
        escritor.println("mundo");
        //escritor.println("fin");

        //escritor.close();

        BufferedReader lector = new BufferedReader(new InputStreamReader(eco.getInputStream()));
        String linea;

        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
        }

        int resultado = eco.waitFor();
        System.out.println("Resultado: " + resultado);
    }
}

     

