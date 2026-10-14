import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Tarea2Ejecutar {
    public static void main(String[] args) {
        String ejecutableJava = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(ejecutableJava, "-cp", classpath, "Tarea2Lector");
        pb.inheritIO();

        try {
            int codigo = pb.start().waitFor();
            if (codigo != 0) {
                System.err.println("El proceso hijo terminó con error (código " + codigo + ").");
            }
            System.exit(codigo);
        } catch (IOException e) {
            System.err.println("No se pudo lanzar el proceso hijo: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Se interrumpió la espera del proceso hijo.");
            System.exit(1);
        }
    }
}
