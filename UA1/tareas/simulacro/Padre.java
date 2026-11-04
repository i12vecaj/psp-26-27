import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Padre {

    public static void main(String[] args) {
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] hijos = new Process[argumentos.length];
        int sumaTotal = 0;

        try {
            // Primero lanzo los 4 hijos para que esten todos en marcha a la vez
            for (int i = 0; i < argumentos.length; i++) {
                hijos[i] = lanzarHijo(argumentos[i]);
            }

            // Despues voy leyendo y esperando a cada uno
            for (int i = 0; i < hijos.length; i++) {
                int suma = leerYSumar(hijos[i]);
                int codigo = hijos[i].waitFor();

                String mensaje = "Hijo " + (i + 1) + " (arg=" + argumentos[i] + "): codigo "
                        + codigo + " -> " + explicarCodigo(codigo);
                if (codigo == 0) {
                    mensaje += ", suma=" + suma;
                }
                System.out.println(mensaje);

                sumaTotal += suma;
            }

            System.out.println("Suma total: " + sumaTotal);

        } catch (IOException e) {
            System.out.println("Error al lanzar o leer un hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Me interrumpieron mientras esperaba a un hijo");
        }
    }

    // Lanza un proceso Hijo con el argumento que le paso
    private static Process lanzarHijo(String argumento) throws IOException {
        return new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"),
                "Hijo", argumento).start();
    }

    // Lee todo lo que escribe el hijo y devuelve la suma de sus numeros
    private static int leerYSumar(Process hijo) throws IOException {
        int suma = 0;
        BufferedReader lector = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
        String linea;
        while ((linea = lector.readLine()) != null) {
            try {
                suma += Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Linea que no es un numero: " + linea);
            }
        }
        lector.close();
        return suma;
    }

    // Devuelve el mensaje que explica cada codigo de salida
    private static String explicarCodigo(int codigo) {
        switch (codigo) {
            case 0:
                return "OK";
            case 1:
                return "falta argumento";
            case 2:
                return "no es un numero";
            case 3:
                return "numero negativo";
            default:
                return "codigo desconocido";
        }
    }
}
