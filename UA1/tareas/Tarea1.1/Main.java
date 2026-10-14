import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
    static void main() throws IOException {
        //CREACION PROCESS BUILDER
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "dir");
        //LANZAR PROCESO CON START
        Process proceso = pb.start();
        //MOSTRAR EL PID
        System.out.println("Proceso con el PID: " + proceso.pid());
        //LEE TODA LA SALIDA
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null){
                numeroLinea++;
                System.out.println(numeroLinea+", " + linea);
            }
        //ESPERAR A QUE TERMINE
            int exit;
            try{
                exit = proceso.waitFor();
                System.out.println("Exit: "+exit +"|| Lineas leidas"+ numeroLinea);
            }catch (Exception e){
                throw new RuntimeException();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }
}