import java.io.*;

public class Tarea2Lector {
    private static final char TERMINADOR = '*';

    public static void main(String[] args) {
        StringBuilder leido = new StringBuilder();
        boolean terminadorRecibido = false;

        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
            int c;
            while ((c = entrada.read()) != -1) {
                if (c == TERMINADOR) {
                    terminadorRecibido = true;
                    break;
                }
                leido.append((char) c);
            }
        } catch (IOException e) {
            System.err.println("Error de entrada/salida al leer: " + e.getMessage());
            System.exit(1);
        }

        if (!terminadorRecibido) {
            System.err.println("Error: la entrada terminó sin recibir el carácter '" + TERMINADOR + "'.");
            System.exit(2);
        }

        // FR2: mostrar toda la información leída
        System.out.println("Información leída:");
        System.out.println(leido);
    }
}