import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso padre: lanza varios procesos Hijo a la vez, suma los numeros que
 * escribe cada uno y muestra su suma y su codigo de salida.
 */
public class Padre {
    public static void main(String[] args) {
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] hijos = new Process[argumentos.length];
        int sumaTotal = 0;

        try {
            // Primero se lanzan todos, para que esten en marcha antes de esperar a ninguno
            String path = System.getProperty("java.class.path");
            for (int i = 0; i < hijos.length; i++) {
                hijos[i] = new ProcessBuilder("java", "-cp", path, "Hijo", argumentos[i]).start();
            }

            // Despues se lee la salida de cada hijo y se espera a que termine
            for (int i = 0; i < hijos.length; i++) {
                int suma = sumarSalida(hijos[i]);
                int codigo = hijos[i].waitFor();
                sumaTotal += suma;

                String resultado = "Hijo " + (i + 1) + " (arg=" + argumentos[i] + "): codigo " + codigo
                        + " -> " + explicarCodigo(codigo);
                if (codigo == 0) {
                    resultado += ", suma=" + suma;
                }
                System.out.println(resultado);
            }

            System.out.println("Suma total: " + sumaTotal);
        } catch (IOException e) {
            System.err.println("Error al lanzar o leer un hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Se interrumpio la espera de un hijo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Un hijo ha escrito algo que no es un numero: " + e.getMessage());
        }
    }

    /* Lee la salida del hijo linea a linea y devuelve la suma de sus numeros */
    static int sumarSalida(Process hijo) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
        int suma = 0;
        String linea;
        while ((linea = lector.readLine()) != null) {
            suma += Integer.parseInt(linea);
        }
        return suma;
    }

    /* Devuelve el mensaje que explica un codigo de salida */
    static String explicarCodigo(int codigo) {
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
                return "error desconocido";
        }
    }
}
