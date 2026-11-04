import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase Padre
 * Lanza múltiples procesos Hijo concurrentemente, lee sus salidas para sumar
 * los resultados numéricos y gestiona los códigos de finalización.
 */
public class Padre {

    public static void main(String[] args) {
        // Argumentos requeridos por el enunciado
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] procesos = new Process[argumentos.length];

        System.out.println("Iniciando procesos hijos...");

        // FR2: Lanzar todos los procesos A LA VEZ antes de esperar a ninguno
        for (int i = 0; i < argumentos.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "-cp",
                        System.getProperty("java.class.path"),
                        "Hijo",
                        argumentos[i]
                );
                procesos[i] = pb.start();
            } catch (IOException e) {
                System.err.println("Error al iniciar el proceso hijo " + (i + 1) + ": " + e.getMessage());
            }
        }

        int sumaTotal = 0;

        // FR3 y FR4: Procesar cada hijo (esperar, leer salida y analizar código)
        for (int i = 0; i < procesos.length; i++) {
            // Si el proceso no pudo iniciarse (por ejemplo, excepción previa), lo saltamos
            if (procesos[i] == null) continue;

            int sumaHijo = 0;
            try {
                // Esperamos a que termine el proceso hijo específico
                procesos[i].waitFor();

                // Leemos lo que el proceso ha escrito en su salida estándar
                BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    try {
                        sumaHijo += Integer.parseInt(linea.trim());
                    } catch (NumberFormatException e) {
                        // Ignoramos si hay alguna línea extraña que no sea número
                    }
                }

                // Obtenemos el código de salida
                int codigoSalida = procesos[i].exitValue();

                // Construimos el mensaje de error o éxito según el código
                String mensaje;
                switch (codigoSalida) {
                    case 0:
                        mensaje = "OK, suma=" + sumaHijo;
                        sumaTotal += sumaHijo;
                        break;
                    case 1:
                        mensaje = "falta argumento";
                        break;
                    case 2:
                        mensaje = "no es un numero";
                        break;
                    case 3:
                        mensaje = "numero negativo";
                        break;
                    default:
                        mensaje = "error desconocido (" + codigoSalida + ")";
                        break;
                }

                // Imprimimos el resultado final formateado
                System.out.printf("Hijo %d (arg=%s): codigo %d -> %s\n",
                        (i + 1), argumentos[i], codigoSalida, mensaje);

            } catch (InterruptedException e) {
                System.err.println("La espera del hijo " + (i + 1) + " fue interrumpida.");
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.err.println("Error al leer la salida del hijo " + (i + 1) + ": " + e.getMessage());
            }
        }

        // Mostrar la suma total al final
        System.out.println("Suma total: " + sumaTotal);
    }
}