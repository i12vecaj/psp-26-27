import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Reto2 {

    public static void main(String[] args) throws Exception {
        Process hijo = new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                Eco.class.getName()
        ).redirectError(ProcessBuilder.Redirect.INHERIT).start();

        try {
            try (PrintWriter escritor =
                         new PrintWriter(hijo.getOutputStream(), true)) {
                escritor.println("hola");
                escritor.println("mundo");
                escritor.println("fin");
            }

            if (!hijo.waitFor(3, TimeUnit.SECONDS)) {
                hijo.destroy();

                if (!hijo.waitFor(1, TimeUnit.SECONDS)) {
                    hijo.destroyForcibly();
                    hijo.waitFor();
                }

                System.out.println("Tiempo de espera agotado.");
                return;
            }

            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(hijo.getInputStream()))) {
                String linea;

                while ((linea = lector.readLine()) != null) {
                    System.out.println(linea);
                }
            }
        } finally {
            if (hijo.isAlive()) {
                hijo.destroyForcibly();
            }
        }

        // Sin "fin" ni cerrar el flujo, Eco espera más datos.
        // El padre quedaría bloqueado si esperase sin límite.
    }

    public static class Eco {
        public static void main(String[] args) throws Exception {
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            String linea;

            while ((linea = entrada.readLine()) != null) {
                if (linea.equals("fin")) {
                    break;
                }

                System.out.println(linea.toUpperCase(Locale.ROOT));
            }
        }
    }
}