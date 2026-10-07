import java.io.IOException;

public class Ejemplo1 {
    public static void main(String[] args) throws IOException  {
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
        for(int i=0; i<1;i++)
        {
            Process p = pb.start();

            long pidHijo = p.pid();
            long pidPadre = ProcessHandle.current().pid();

            System.out.println("Pid del padre: "+pidPadre);
            System.out.println("Pid del hijo: "+pidHijo);
        }

    }
}//Ejemplo1