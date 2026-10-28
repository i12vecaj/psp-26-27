import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reto1 {

    static void main() {
        boolean linea;
        try {
            ProcessBuilder pb = (new ProcessBuilder("java","-version"));
            Process process = pb.start();


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            System.out.println("Salida del proceso: ");
            while ((linea = reader.readLine() != null)){
                System.out.println(linea);
            }

            int exitCode = process.waitFor();
            System.out.println("Codigo de Salida"+ exitCode);

        }catch (IOException | InterruptedException e){

        }

    }


}
