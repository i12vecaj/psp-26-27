
import java.util.Scanner;

public class Leer{

    public static void main(String[] args) {

        // FR1: Leer caracteres hasta encontrar un asterisco
        try (Scanner sc = new Scanner(System.in)) {

            StringBuilder texto = new StringBuilder();

            System.out.println("Introduce texto (termina con *):");

            while (true) {
                String linea = sc.nextLine();

                if (linea.contains("*")) {
                    texto.append(linea, 0, linea.indexOf("*"));
                    break;
                }

                texto.append(linea).append("\n");
            }

            // FR2: Mostrar el texto introducido
            System.out.println("Texto introducido: " + texto);

        } catch (Exception e) {
            System.err.println("Error al leer el texto: " + e.getMessage());
        }
    }
}
