import java.io.*;

/**
 * Programa lanzador que ejecuta el programa LectorEntrada (FR3), 
 * le envía texto a través de su entrada estándar e imprime los resultados obtenidos.
 */
public class LanzadorProceso {

    public static void main(String[] args) {
        // Obtenemos la ruta del ejecutable java y el classpath actual para invocar la otra clase
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

        // Configuramos el ProcessBuilder para ejecutar la clase LectorEntrada
        ProcessBuilder pb = new ProcessBuilder(javaBin, "-cp", classpath, "LectorEntrada");

        Process proceso = null;

        try {
            // FR3: Lanzar el proceso hijo
            proceso = pb.start();
            System.out.println("Proceso hijo lanzado con PID: " + proceso.pid());

            // Enviamos datos hacia la entrada estándar del proceso hijo (incluyendo el asterisco '*')
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()))) {
                writer.write("Hola, este es un texto de prueba enviado desde el lanzador*\n");
                writer.flush();
            } catch (IOException e) {
                System.err.println("Error al escribir datos en el flujo de entrada del proceso: " + e.getMessage());
            }

            // Leemos la salida generada por el proceso hijo
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                System.out.println("\n[Salida capturada del proceso hijo]:");
                while ((linea = reader.readLine()) != null) {
                    System.out.println("  > " + linea);
                }
            } catch (IOException e) {
                System.err.println("Error al leer la salida del proceso hijo: " + e.getMessage());
            }

            // Esperar a que el proceso termine su ejecución y comprobar su código de salida
            int codigoSalida = proceso.waitFor();
            System.out.println("\nEl proceso finalizó con código de salida: " + codigoSalida);

            if (codigoSalida == 0) {
                System.out.println("Estado: Ejecución correcta.");
            } else {
                System.err.println("Estado: El proceso hijo reportó un error (código distinto de 0).");
            }

        } catch (IOException e) {
            // Control de errores ante fallos de inicio del proceso o E/S
            System.err.println("Error de E/S al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            // Control de errores si el hilo de espera (waitFor) es interrumpido
            System.err.println("El proceso principal fue interrumpido mientras esperaba al hijo: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaurar elflag de interrupción
        } catch (Exception e) {
            // Control de errores genérico para imprevistos
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        } finally {
            // Asegurarse de liberar recursos forzando la destrucción si el proceso se queda colgado
            if (proceso != null && proceso.isAlive()) {
                proceso.destroy();
            }
        }
    }
}