package UA1.tareas.Tarea2.solucion;
import java.io.File;
import java.io.IOException;


public class LanzadorLectura {

    static final String PROGRAMA = "UA1/tareas/Tarea2/solucion/LeerHastaAsterisco.java";

    public static void main(String[] args) {
        File fichero = new File(PROGRAMA);
        if (!fichero.exists()) {
            System.err.println("Error: no se encuentra " + fichero.getAbsolutePath());
            System.err.println("Ejecuta este programa desde la raíz del repositorio.");
            System.exit(1);
        }

        ProcessBuilder pb = new ProcessBuilder("java", PROGRAMA);

        
        pb.inheritIO();

        try {
            Process p = pb.start();
            System.out.println("Proceso hijo lanzado con PID: " + p.pid());

            int codigoSalida = p.waitFor();


            switch (codigoSalida) {
                case 0 -> System.out.println("Código de salida: 0 (Correcto)");
                case 1 -> System.out.println("Código de salida: 1 (No se escribió el *)");
                case 2 -> System.out.println("Código de salida: 2 (Error de lectura)");
                default -> System.out.println("Código de salida: " + codigoSalida + " (Error desconocido)");
            }
        } catch (IOException e) {
            System.err.println("Error al lanzar el proceso: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Se interrumpió la espera del proceso: " + e.getMessage());
            System.exit(1);
        }
    }
}
