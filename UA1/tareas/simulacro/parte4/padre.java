import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class padre {
    public static void main(String[] args) {
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] hijos = new Process[argumentos.length];

        try {
            // Arrancamos todos los hijos antes de esperar a ninguno.
            for (int i = 0; i < argumentos.length; i++) {
                hijos[i] = new ProcessBuilder(
                        "java", "-cp", System.getProperty("java.class.path"),
                        "Hijo", argumentos[i]
                ).redirectError(ProcessBuilder.Redirect.INHERIT).start();
            }

            long sumaTotal = 0;

            // Leemos la salida de cada hijo y recogemos su código.
            for (int i = 0; i < hijos.length; i++) {
                long suma = leerSuma(hijos[i]);
                int codigo = hijos[i].waitFor();
                String mensaje = mensajeSalida(codigo);

                if (codigo == 0) {
                    sumaTotal += suma;
                    mensaje += ", suma=" + suma;
                }

                System.out.printf("Hijo %d (arg=%s): codigo %d -> %s%n",
                        i + 1, argumentos[i], codigo, mensaje);
            }

            System.out.println("Suma total: " + sumaTotal);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al ejecutar un hijo o leer su salida: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El padre fue interrumpido");
        } finally {
            for (Process hijo : hijos) {
                if (hijo != null && hijo.isAlive()) {
                    hijo.destroy();
                }
            }
        }
    }

    private static long leerSuma(Process hijo) throws IOException {
        long suma = 0;

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(hijo.getInputStream()))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                suma += Integer.parseInt(linea);
            }
        }

        return suma;
    }

    private static String mensajeSalida(int codigo) {
        switch (codigo) {
            case 0: return "OK";
            case 1: return "falta argumento";
            case 2: return "no es un numero";
            case 3: return "numero negativo";
            case 4: return "hijo interrumpido";
            default: return "error desconocido";
        }
    }
}