import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class reto1 {
    static void main(String[] args) {
        String[] command = {"java", "Version"};
        ProcessBuilder pb = new ProcessBuilder(command);

        try{
            Process p = pb.start();

            BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("Salida");
            String linea;
            while ((linea = stdout.readLine()) != null ){
                System.out.println(linea);
            }

            System.out.println("Salida de error");
            while ((linea = stderr.readLine()) != null ){
                System.out.println(linea);
            }

            int exitcode = p.waitFor();
            System.out.println("El proceso termino" + exitcode);


        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        try{
            System.out.println("Ampliacion");
            ProcessBuilder pbinv = new ProcessBuilder("no existe");
            Process pinv = pbinv.start();

        }catch (Exception e){
            System.out.println("Se ha capturado correctamente");
            System.out.println("Error " + e.getMessage());
        }

    }
}
