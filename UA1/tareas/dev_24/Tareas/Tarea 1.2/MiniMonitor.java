import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;

public class MiniMonitor{

    static final boolean WIN= System.getProperty("os.name").toLowerCase().contains("win");
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                : new String[]{"ls", "-l"};

        // creacion del processBuilder y lo que hace
        ProcessBuilder pb = new ProcessBuilder("cmd","/c","dir");
        //las repeticiones que hara nuestro proceso
        for (int i = 0; i < 1; i++) {
            //Inicio del proceso
            Process p = pb.start();
            //Localizar el Pid del proceso
            System.out.println(p.pid());
            //lectura del archivo linea a linea
            try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String linea;
                int numlinea = 1 ;

                while ((linea = br.readLine()) !=null){
                    System.out.printf("%4d: %s%n",numlinea++,linea);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //lectura de lineas y esperas.
            int exitva1;
            try{
                exitva1= p.waitFor();
                System.out.println("Lineas de codigo de salida y leidas(0=Correcto): "+exitva1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}