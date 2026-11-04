import java.io.*;
import java.util.concurrent.TimeUnit;

public class Reto2 {
    static void main(String[] args) {
        ProcessBuilder ps = new ProcessBuilder("java","-cp",System.getProperty("java.class.path"),"Eco");

        try{
            Process proceso = ps.start();

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(proceso.getOutputStream()),true);

            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));


            pw.println("Hola");
            System.out.println("mundo recibido"+br.lines());


            pw.println("Mundo");
            System.out.println("mundo recibido"+br.lines());

            pw.println("Fin");
            System.out.println("mundo recibido"+br.lines());

            pw.close();
            boolean finalizado = proceso.waitFor(3, TimeUnit.SECONDS);

            if (finalizado){
                System.out.println(proceso.exitValue());
            }else {
                proceso.destroy();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
