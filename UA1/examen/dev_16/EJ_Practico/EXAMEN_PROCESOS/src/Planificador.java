import java.io.*;
import java.util.concurrent.TimeUnit;

public class Planificador {
        public static void main(String[] args) throws InterruptedException, IOException {
            System.out.println("Caso normal: Se envia a hijo");
            lanzarEco(true);

            System.out.println();
            System.out.println("Ampliación: NO envío fin ni cierro el flujo");
            lanzarEco(false);
        }
        private static void lanzarEco(boolean terminarBien) throws InterruptedException, IOException {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp",
                    System.getProperty("java.class.path"), "Eco");
            Process hijo = pb.start();

            // Lo que el padre escribe aquí es la ENTRADA estándar del hijo
            PrintWriter alHijo = new PrintWriter(hijo.getOutputStream(), true); // autoflush
            alHijo.println("Hola");
            alHijo.println("trabajando");
            if (terminarBien) {
                alHijo.println("terminado");
                alHijo.close();
            }
            boolean termino = hijo.waitFor(3, TimeUnit.SECONDS);
            if (!termino) {
                System.out.println("El hijo sigue esperando datos tras 3 s lo destruyo");
                hijo.destroy();
                hijo.waitFor();
            }

            BufferedReader delHijo = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
            String linea;
            while ((linea = delHijo.readLine()) != null) {
                System.out.println("Mensaje recibido: " + linea);
            }
            System.out.println("Hola, mensaje a Falla " + hijo.exitValue());
        }
        }


