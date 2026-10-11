import java.io.IOException;
import java.lang.management.ManagementFactory;

public class tarea1 {

    public static void main(String[] args) throws IOException {

        // Obtener el PID del proceso padre (el programa Java)
        long pidPadre = ProcessHandle.current().pid();

        System.out.println("PID del proceso padre: " + pidPadre);

        // Crear el proceso hijo
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
        Process p = pb.start();

        // Obtener el PID del proceso hijo
        long pidHijo = p.pid();

        System.out.println("PID del proceso hijo: " + pidHijo);
    }
}