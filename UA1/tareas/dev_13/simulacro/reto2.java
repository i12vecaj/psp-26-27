import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class reto2 {
    static void main(String[] args) {
        String[] command = {"java", "cp", System.getProperty("java.class.path"), "eco"};
        ProcessBuilder pb = new ProcessBuilder(command);

        try{
            Process p = pb.start();

            PrintWriter writer = new PrintWriter(p.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            writer.println("hola");
            writer.println("mundo");
            writer.println("fin");
            writer.close();

            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Respuesta hijo " + linea);
            }

            boolean terminado = p.waitFor(3, TimeUnit.SECONDS);
            if (terminado){
                System.out.println("el proceso termino" + p.exitValue());
            } else {
                System.out.println("el proceso no termino");
                p.destroy();
            }
        }catch (IOException |InterruptedException e){
            e.printStackTrace();
        }

    }
}
