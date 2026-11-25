
import java.io.IOException;

public class Ejemplo1v {
    public static void main(String[] args) throws IOException  {
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");

        for(int i=0; i<1;i++)
        {
            Process p = pb.start();
            //Mostramos el identificador del proceso p
            System.out.println("el id del proceso hijo es "+p.pid());
            // Mostramos el identificador del proceso Padre
            long pidPadre = ProcessHandle.current().pid();
            System.out.println("el id del proceso padre es "+pidPadre);
        }

    }
}//Ejemplo1

