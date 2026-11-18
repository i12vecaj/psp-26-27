import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso hijo: lee líneas de su entrada estándar hasta recibir "fin"
 * y devuelve cada una en mayúsculas por su salida estándar.
 */
public class Eco {
    public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String linea;
        while ((linea = entrada.readLine()) != null) {
            if (linea.equalsIgnoreCase("fin")) {
                break;
            }
            System.out.println(linea.toUpperCase());
        }
        System.exit(0);
    }
}
