/**
 * @file      Padre.java
 * @brief     Lanza cuatro procesos Hijo a la vez, suma lo que escribe cada uno y explica su codigo de salida
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Padre {

    // argumentos de los cuatro hijos
    static final String[] ARGUMENTOS = {"4", "6", "-2", "abc"};

    // FR4: que quiere decir cada codigo de salida del hijo
    static String explicar(int codigo) {
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

    // FR3: lee todo lo que escribe un hijo y suma los numeros
    static int sumarSalida(Process hijo) throws IOException {
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
        return suma;
    }

    public static void main(String[] args) {
        Process[] hijos = new Process[ARGUMENTOS.length];
        String classpath = System.getProperty("java.class.path");

        // FR2: se lanzan los cuatro hijos antes de esperar a ninguno
        for (int i = 0; i < ARGUMENTOS.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, Hijo.class.getName(), ARGUMENTOS[i]);
                hijos[i] = pb.start();
            } catch (IOException e) {
                System.out.println("No se pudo lanzar el hijo " + (i + 1) + ": " + e.getMessage());
            }
        }

        // FR3 y FR4: ahora se lee y se espera a cada hijo
        int sumaTotal = 0;
        for (int i = 0; i < hijos.length; i++) {
            if (hijos[i] == null) {
                continue;
            }
            try {
                int suma = sumarSalida(hijos[i]);
                int codigo = hijos[i].waitFor();
                String texto = "Hijo " + (i + 1) + " (arg=" + ARGUMENTOS[i] + "): codigo " + codigo + " -> " + explicar(codigo);
                if (codigo == 0) {
                    texto += ", suma=" + suma;
                    sumaTotal += suma;
                }
                System.out.println(texto);
            } catch (IOException e) {
                System.out.println("Error al leer el hijo " + (i + 1) + ": " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Se interrumpio la espera del hijo " + (i + 1));
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Suma total: " + sumaTotal);
    }
}
