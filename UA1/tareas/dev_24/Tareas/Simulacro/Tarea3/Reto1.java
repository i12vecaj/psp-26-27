import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reto1 {
    static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java","-version");

        try{
            Process process = pb.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                String linea ;
                while ((linea= br.readLine())!=null){
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            Process proceso = pb.start();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getErrorStream()))){
                String linea ;
                while ((linea= br.readLine())!=null){
                    System.out.println(linea);
                }
            }

            int salidaCodigo = proceso.waitFor();
            System.out.println("salida proceso "+salidaCodigo);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
