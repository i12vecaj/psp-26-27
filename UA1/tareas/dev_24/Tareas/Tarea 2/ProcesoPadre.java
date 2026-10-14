import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcesoPadre {

    public static void main(String[] args) {
        String mensajeAEnviar = "Hola desde el proceso padre. Esta es una prueba de comunicación.*Este texto no debería leerse";

        // Configuración del comando: 'java ProcesoHijo'
        ProcessBuilder processBuilder = new ProcessBuilder("java", "ProcesoHijo");
        
        // Define el directorio de ejecución como el directorio actual del padre
        processBuilder.directory(new File("."));

        try {
            // Inicia el proceso hijo
            Process procesoHijo = processBuilder.start();

            // Flujo para ENVIAR datos a la entrada estándar del proceso hijo
            try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(procesoHijo.getOutputStream()))) {
                escritor.write(mensajeAEnviar);
                escritor.flush(); // Asegura el envío de los datos
            }

            // Flujo para LEER la salida producida por el proceso hijo
            try (BufferedReader lectorHijo = new BufferedReader(new InputStreamReader(procesoHijo.getInputStream()))) {
                String linea;
                System.out.println("=== Salida capturada desde el Proceso Hijo ===");
                while ((linea = lectorHijo.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // Esperar a que el proceso hijo finalice y verificar el código de salida
            int codigoSalida = procesoHijo.waitFor();
            System.out.println("===============================================");
            System.out.println("El proceso hijo terminó con código de salida: " + codigoSalida);

        } catch (IOException e) {
            System.err.println("Error al iniciar o comunicarse con el proceso hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La espera del proceso hijo fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción
        }
    }
}