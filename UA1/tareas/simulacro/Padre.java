import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Programa padre que lanza 4 procesos del hijo a la vez, lee los numeros que van
 * sacando para sumarlos y muestra cómo termina cada uno.
 */
public class Padre {
    public static void main(String[] args) {
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] procesos = new Process[argumentos.length];
        String rutaClases = System.getProperty("java.class.path");
        int sumaTotal = 0;
        // 1. Lanzamos los 4 hijos a la vez en un bucle sin esperar
        try {

            for (int i = 0; i < argumentos.length; i++) {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", rutaClases, "Hijo", argumentos[i]);
                procesos[i] = pb.start();
            }

            // 2. En otro bucle vamos leyendo lo que dice cada hijo y espero a que acabe
            for (int i = 0; i < procesos.length; i++) {
                Process hijo = procesos[i];
                int sumaHijo = 0;

                // Lee linea por linea lo que el hijo escribe por pantalla
                try (BufferedReader lector = new BufferedReader(new InputStreamReader(hijo.getInputStream()))) {
                    String linea;
                    while ((linea = lector.readLine()) != null) {
                        sumaHijo += Integer.parseInt(linea);
                    }
                }

                // Espero a que el hijo termine y guardo su código de salida
                int codigo = hijo.waitFor();

                // 3. Muestro el resultado segun el código que haya devuelto
                String cabecera = "Hijo " + (i + 1) + " (arg=" + argumentos[i] + "): codigo " + codigo + " -> ";

                switch (codigo) {
                    case 0:
                        System.out.println(cabecera + "OK, suma=" + sumaHijo);
                        sumaTotal += sumaHijo;
                        break;
                    case 1:
                        System.out.println(cabecera + "falta argumento");
                        break;
                    case 2:
                        System.out.println(cabecera + "no es un numero");
                        break;
                    case 3:
                        System.out.println(cabecera + "numero negativo");
                        break;
                }
            }

            // 4. Al final muestro la suma de todos los hijos que funcionaron bien
            System.out.println("Suma total: " + sumaTotal);

        } catch (IOException | InterruptedException e) {
            System.out.println("Hubo un problema al ejecutar o esperar a los procesos: " + e.getMessage());
        }
    }
}
