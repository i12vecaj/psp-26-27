import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Reto 2 · Enviar datos al hijo.
 * El padre envía "hola", "mundo" y "fin" al proceso Eco y muestra su respuesta.
 * Ampliación: espera con waitFor(3, SECONDS) y, si no termina, destroy().
 */
public class Reto2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=== Caso normal: envío hola, mundo y fin ===");
        lanzarEco(true);

        System.out.println();
        System.out.println("=== Ampliación: NO envío fin ni cierro el flujo ===");
        lanzarEco(false);
    }

    private static void lanzarEco(boolean terminarBien) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp",
                System.getProperty("java.class.path"), "Eco");
        Process hijo = pb.start();

        // Lo que el padre escribe aquí es la ENTRADA estándar del hijo
        PrintWriter alHijo = new PrintWriter(hijo.getOutputStream(), true); // autoflush
        alHijo.println("hola");
        alHijo.println("mundo");
        if (terminarBien) {
            alHijo.println("fin");
            alHijo.close();
        }

        // Si no ha terminado en 3 segundos, lo matamos
        boolean termino = hijo.waitFor(3, TimeUnit.SECONDS);
        if (!termino) {
            System.out.println("El hijo sigue esperando datos tras 3 s -> destroy()");
            hijo.destroy();
            hijo.waitFor();
        }

        // Lo que el hijo escribe en su salida lo leemos con getInputStream()
        BufferedReader delHijo = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
        String linea;
        while ((linea = delHijo.readLine()) != null) {
            System.out.println("Eco responde: " + linea);
        }
        System.out.println("Código de salida del hijo: " + hijo.exitValue());
    }
}
