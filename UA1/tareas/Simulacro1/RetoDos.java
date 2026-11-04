import java.io.*;
import java.util.concurrent.TimeUnit;

public class RetoDos {
    public static void main(String[] args) {
        try {
            // 1. Lanzar el proceso hijo (usamos la clase que has creado)
            // Nota: La clase hija (RetoDos o Eco) ya debe estar compilada (.class)
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "RetoDos");
            Process hijo = pb.start();

            // 2. Preparamos el flujo para ENVIAR datos al hijo
            // Usamos true para activar el 'autoflush' (enviar inmediatamente sin retener en memoria)
            PrintWriter escritor = new PrintWriter(hijo.getOutputStream(), true);

            // 3. Preparamos el flujo para LEER las respuestas del hijo
            BufferedReader lector = new BufferedReader(new InputStreamReader(hijo.getInputStream()));

            // 4. Escribimos hacia el proceso hijo
            System.out.println("[Padre] Enviando mensajes...");
            escritor.println("hola");
            escritor.println("mundo");
            escritor.println("fin");

            // BUENA PRÁCTICA: Cerrar el flujo para avisar al hijo que no enviaremos más datos
            escritor.close();

            // 5. Leemos lo que el hijo imprime en su consola
            System.out.println("\n[Padre] Leyendo respuestas del hijo:");
            String respuesta;
            while ((respuesta = lector.readLine()) != null) {
                System.out.println(" -> " + respuesta);
            }

            // 6. AMPLIACIÓN: Espera con tiempo límite (Timeout)
            // Esperamos un máximo de 3 segundos. Devuelve 'true' si terminó a tiempo.
            boolean termino = hijo.waitFor(3, TimeUnit.SECONDS);

            System.out.println("\n==================================");
            if (termino) {
                System.out.println("Proceso finalizado con normalidad. Exit code: " + hijo.exitValue());
            } else {
                System.out.println("⚠️ El proceso hijo no respondió a tiempo. Destruyendo...");
                hijo.destroy(); // Forzamos el cierre (como matar el proceso en el Administrador de Tareas)
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}