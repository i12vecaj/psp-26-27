import java.io.IOException;
import java.util.Scanner;
public class Ejecuta {
         /* Lanza el programa Procesos*/
        static void main(String[] args) throws IOException, InterruptedException {
            // Mismo classpath con el que se ejecuta este programa (donde esta Procesos.class)
            String path = System.getProperty("java.class.path");
            Scanner sc = new Scanner(System.in);
            System.out.println("Escribe un numero o cadena y te detecto lo que es");
            String cosa = sc.nextLine();

            Process proceso = new ProcessBuilder("java", "-cp", path, "Procesos", cosa).inheritIO().start();

            int codigo = proceso.waitFor();

            /**
             * Switch en base a lo que el programa Procesos detecta*/
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
