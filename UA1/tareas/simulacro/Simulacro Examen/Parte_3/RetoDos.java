import java.io.*;
import java.util.concurrent.TimeUnit;

public class RetoDos {
    public static void main(String[] args) {
        try {
            // 1. Crear el proceso hijo ejecutando la clase Eco
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp",
                    System.getProperty("java.class.path"),
                    "Eco"
            );
            Process hijo = pb.start();

            // 2. Flujo para ENVIAR datos al hijo (OutputStream del proceso)
            PrintWriter escritor = new PrintWriter(new OutputStreamWriter(hijo.getOutputStream()), true);

            // 3. Flujo para LEER la respuesta del hijo (InputStream del proceso)
            BufferedReader lector = new BufferedReader(new InputStreamReader(hijo.getInputStream()));

            // 4. Enviar los mensajes al hijo
            escritor.println("hola");
            escritor.println("mundo");
            escritor.println("fin"); // Marca el final según la lógica de Eco

            // 5. Leer las respuestas que procesa el hijo
            String respuesta;
            while ((respuesta = lector.readLine()) != null) {
                System.out.println("Padre recibe: " + respuesta);
            }

            // 6. Cerrar el flujo de salida y esperar a que el hijo finalice libremente (con timeout)
            escritor.close();

            boolean terminado = hijo.waitFor(3, TimeUnit.SECONDS);
            if (!terminado) {
                System.out.println("El hijo no terminó a tiempo. Forzando cierre...");
                hijo.destroy();
            } else {
                System.out.println("El proceso hijo ha terminado correctamente con código: " + hijo.exitValue());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
