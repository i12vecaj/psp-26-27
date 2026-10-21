import java.io.IOException;
import java.util.Scanner;

public class Ejecutar {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce un argumento:");
        String argumento = sc.nextLine();

        try {
            String ruta = System.getProperty("java.class.path");

            ProcessBuilder pb;

            if (argumento.isEmpty()) {
                pb = new ProcessBuilder("java", "-cp", ruta, "ProgramaPrincipal");
            } else {
                pb = new ProcessBuilder("java", "-cp", ruta, "ProgramaPrincipal", argumento);
            }

            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process p = pb.start();

            int resultado = p.waitFor();

            if (resultado == 1) {
                System.out.println("Error: no se ha introducido ningun argumento");
            } else if (resultado == 2) {
                System.out.println("Error: el argumento es una cadena");
            } else if (resultado == 3) {
                System.out.println("Error: el numero es negativo");
            } else if (resultado == 0) {
                System.out.println("El numero es correcto");
            } else {
                System.out.println("Error desconocido");
            }

        } catch (IOException e) {
            System.out.println("Error al ejecutar el programa");
        } catch (InterruptedException e) {
            System.out.println("El proceso ha sido interrumpido");
            Thread.currentThread().interrupt();
        }

        sc.close();
    }
}