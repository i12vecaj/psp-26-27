import java.io.IOException;
import java.util.Scanner;

public class EjecutarIdentificador{
    public static void main (String[] args){
        String path = System.getProperty("java.class.path");
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce algo");
        String cosa = sc.nextLine();

        ProcessBuilder pb = new ProcessBuilder("java", "-cp", path, "Identificador", cosa);
try {
    Process proceso = pb.start();
    int id = proceso.waitFor();
    switch (id) {
        case 0:
            System.out.println("Todo correcto");
        case 1:
            System.out.println("La longitud del argumento es menor a 1");
            break;
        case 2:
            System.out.println("El argumento es un número entero menor a 0");
            break;
        case 3:
            System.out.println("El argumento es o un String o un entero");
            break;
        default:
            System.out.println("Resultado desconocido");

    }
} catch (IOException | InterruptedException e) {
    throw new RuntimeException(e);
}
    }
}