import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reto 1 · Leer la salida de un proceso.
 * Lanza "java -version", muestra lo que imprime y su código de salida.
 * Ampliación: lanza un comando que no existe y captura la excepción.
 */
public class Reto1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Process p = new ProcessBuilder("java", "-version").start();

        System.out.println("--- Salida estándar (getInputStream) ---");
        imprimir(p.getInputStream());
        System.out.println("--- Salida de error (getErrorStream) ---");
        imprimir(p.getErrorStream());

        int codigo = p.waitFor();
        System.out.println("Código de salida: " + codigo);

        // Ampliación: un comando que no existe
        System.out.println();
        System.out.println("--- Ampliación: comando inexistente ---");
        try {
            new ProcessBuilder("comandoQueNoExiste").start();
        } catch (IOException e) {
            System.out.println("Excepción capturada: " + e.getClass().getSimpleName());
            System.out.println("Mensaje: " + e.getMessage());
        }
    }

    private static void imprimir(InputStream flujo) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(flujo));
        String linea;
        boolean vacio = true;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
            vacio = false;
        }
        if (vacio) {
            System.out.println("(vacío)");
        }
    }
}
