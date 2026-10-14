import java.io.IOException;

/**
 * Programa encargado de leer caracteres desde la entrada estándar
 * de manera continuada hasta encontrar el carácter de terminación '*'.
 * Una vez encontrado, muestra toda la información acumulada.
 */
public class LectorEntrada {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        try {
            int caracter;
            // FR1: Lee carácter a carácter desde la entrada estándar hasta recibir '*'
            while ((caracter = System.in.read()) != -1) {
                char c = (char) caracter;

                if (c == '*') {
                    break; // Criterio de parada alcanzado
                }

                sb.append(c);
            }

            // FR2: Muestra por pantalla toda la información leída una vez finalizada la lectura
            System.out.println("=== INFORMACIÓN LEÍDA ===");
            System.out.println(sb.toString());

        } catch (IOException e) {
            // Control de errores de E/S
            System.err.println("Error crítico de E/S en LectorEntrada: " + e.getMessage());
            System.exit(1);
        }
    }
}