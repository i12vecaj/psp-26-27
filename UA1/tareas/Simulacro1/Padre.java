import java.io.*;

/**
 * Clase Padre: Lanza procesos concurrentes, suma sus resultados y controla errores.
 */
public class Padre {
    public static void main(String[] args) {
        String[] argsHijo = {"4", "6", "-2", "abc"};
        Process[] procesos = new Process[4];
        String cp = System.getProperty("java.class.path");

        // Array para simplificar el switch de mensajes de error (índice = código de error)
        String[] mensajes = {"OK", "falta argumento", "no es un numero", "numero negativo"};

        try {
            // FR2: Lanzar los 4 procesos A LA VEZ
            for (int i = 0; i < 4; i++) {
                procesos[i] = new ProcessBuilder("java", "-cp", cp, "Hijo", argsHijo[i]).start();
            }

            int sumaTotal = 0;

            // FR3: Leer cada hijo (mientras se ejecutan) y calcular sumas
            for (int i = 0; i < 4; i++) {
                BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()));
                int sumaHijo = 0;
                String linea;

                while ((linea = br.readLine()) != null) {
                    sumaHijo += Integer.parseInt(linea); // Lee y suma al vuelo
                }

                procesos[i].waitFor(); // Asegurar que terminó
                int cod = procesos[i].exitValue(); // FR4: Código de salida

                if (cod == 0) sumaTotal += sumaHijo; // Solo sumar si es OK

                // FR4: Imprimir resultados de forma compacta
                String msg = (cod >= 0 && cod <= 3) ? mensajes[cod] : "error desconocido";
                String extra = (cod == 0) ? ", suma=" + sumaHijo : "";

                System.out.printf("Hijo %d (arg=%s): codigo %d -> %s%s\n",
                        i + 1, argsHijo[i], cod, msg, extra);
            }

            System.out.println("Suma total: " + sumaTotal);

        } catch (Exception e) {
            // FR4: Control de errores simplificado en un solo bloque
            System.err.println("Error general en el proceso Padre: " + e.getMessage());
        }
    }
}