
import java.io.IOException;

public class Ejemplo1 {
    public static void main(String[] args) throws IOException  {
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
        for (int i = 0; i < 1; i++) {
            Process p = pb.start();
            System.out.println("PID del proceso de explorer hijo: " + p.pid());
        }
        System.out.println("PID del padre: " + ProcessHandle.current().pid());
    }
}//Ejemplo1