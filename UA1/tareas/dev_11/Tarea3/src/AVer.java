import java.io.IOException;
import java.util.Scanner;

public class AVer {
    /**
     * Lanza el programa Que es*/
    static void main(String[] args) throws IOException, InterruptedException {
        String path = "C:\\Users\\Martín\\OneDrive\\Escritorio\\PSP\\psp-26-27\\UA1\\tareas\\dev_11\\Tarea3\\out\\production\\Tarea3";
        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe un numero o cadena y te detecto lo que es");
        String cosa = sc.nextLine();
        
        Process proceso = new ProcessBuilder("java", "-cp", path, "QueEs", cosa).inheritIO().start();

        int codigo = proceso.waitFor();

        /**
         * Switch en base a lo que el programa QueEs detecta*/
        switch (codigo) {
            case 2:
                System.out.println("Es una cadena");
                break;
            case 1:
                System.out.println("No hay argumentos");
                break;
            case 3:
                System.out.println("Es un entero negativo");
                break;
            case 0:
                System.out.println("Es un entero positivo");
                break;
            default:
                System.err.println("No deberias leerme");
        }


    }
}
