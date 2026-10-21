import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LanzadorProceso {

    public static void main(String[] args) {

        String argumentoPrueba = "-5";

        ProcessBuilder processBuilder = new ProcessBuilder("java", "ValidadorArgumentos", argumentoPrueba);

        try {
            System.out.println("========================================");
            System.out.println("Iniciando la ejecución del proceso...");
            System.out.println("Argumento enviado: '" + argumentoPrueba + "'");
            System.out.println("========================================");

            Process proceso = processBuilder.start();

            BufferedReader lectorSalida = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = lectorSalida.readLine()) != null) {
                System.out.println("[Salida Estándar] " + linea);
            }

            // Leer la salida de error estándar del proceso hijo
            BufferedReader lectorError = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            while ((linea = lectorError.readLine()) != null) {
                System.err.println("[Salida de Error]  " + linea);
            }

            int codigoSalida = proceso.waitFor();


            System.out.println("\n----------------------------------------");
            System.out.println("El proceso ha finalizado con el código: " + codigoSalida);

            switch (codigoSalida) {
                case 0:
                    System.out.println("Interpretación: Éxito. El argumento es un número entero válido y mayor o igual a 0.");
                    break;
                case 1:
                    System.out.println("Interpretación: Error. El número de argumentos es menor que 1.");
                    break;
                case 2:
                    System.out.println("Interpretación: Error. El argumento introducido es una cadena de texto.");
                    break;
                case 3:
                    System.out.println("Interpretación: Error. El argumento es un número entero menor que 0.");
                    break;
                default:
                    System.out.println("Interpretación: Código de salida desconocido o no contemplado.");
                    break;
            }
            System.out.println("----------------------------------------");

        } catch (IOException e) {

            System.err.println("[Error de E/S] No se pudo ejecutar el proceso correctamente.");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {

            System.err.println("[Error de Interrupción] El proceso fue interrumpido durante su espera.");
            Thread.currentThread().interrupt(); // Restablecer la bandera de interrupción
        } catch (Exception e) {

            System.err.println("[Error Inesperado] Ocurrió un fallo general en la ejecución.");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }
}