import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Padre: lanza 4 procesos Hijo a la vez con distintos argumentos, lee lo que
 * escribe cada uno, suma los numeros y muestra la suma de cada hijo, su codigo
 * de salida explicado y la suma total.
 */
public class Padre {

    // explica el codigo de salida del hijo
    static String explicar(int codigo) {
        switch (codigo) {
            case 0: return "OK";
            case 1: return "falta argumento";
            case 2: return "no es un numero";
            case 3: return "numero negativo";
            default: return "error desconocido";
        }
    }

    public static void main(String[] args) {
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] hijos = new Process[argumentos.length];
        String cp = System.getProperty("java.class.path");
        int sumaTotal = 0;

        // FR2: primero arranco todos los hijos para que vayan a la vez
        for (int i = 0; i < argumentos.length; i++) {
            try {
                hijos[i] = new ProcessBuilder("java", "-cp", cp, "Hijo", argumentos[i]).start();
            } catch (IOException e) {
                System.out.println("No se ha podido lanzar el hijo " + (i + 1) + ": " + e.getMessage());
            }
        }

        // FR3 y FR4: despues leo y espero a cada uno
        for (int i = 0; i < hijos.length; i++) {
            if (hijos[i] == null) {
                continue;   // este hijo no se pudo lanzar
            }
            int suma = 0;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(hijos[i].getInputStream()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    suma += Integer.parseInt(linea.trim());
                }
                br.close();

                int codigo = hijos[i].waitFor();
                String mensaje = "Hijo " + (i + 1) + " (arg=" + argumentos[i] + "): codigo " + codigo + " -> " + explicar(codigo);
                if (codigo == 0) {
                    mensaje += ", suma=" + suma;
                    sumaTotal += suma;
                }
                System.out.println(mensaje);
            } catch (IOException e) {
                System.out.println("Error leyendo al hijo " + (i + 1) + ": " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Me han interrumpido esperando al hijo " + (i + 1));
            } catch (NumberFormatException e) {
                System.out.println("El hijo " + (i + 1) + " ha escrito algo que no es un numero");
            }
        }

        System.out.println("Suma total: " + sumaTotal);
    }
}
