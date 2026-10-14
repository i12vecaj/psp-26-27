package com.ceslopedevega.procesos;

import java.util.Scanner;

/**
 * Java no tiene fork(), asi que el proceso hijo se crea lanzando otra JVM
 * con ProcessBuilder que ejecuta esta misma clase pasandole el valor.
 */
public class Tarea1 {

    public static void main(String[] args) {

        // ---------- PROCESO HIJO (se ejecuta con un argumento) ----------
        if (args.length == 1) {
            try {
                int variable = Integer.parseInt(args[0]);
                variable = variable + 4;                          // FR4: el hijo suma 4
                System.out.println("[HIJO]  Valor tras sumar 4: " + variable);
            } catch (NumberFormatException e) {
                System.err.println("Error (hijo): argumento no valido");
                System.exit(1);
            }
            return;
        }

        // ---------- PROCESO PADRE ----------
        try {
            Scanner teclado = new Scanner(System.in);
            System.out.print("Introduce un numero entero: ");   // FR2: pedir variable
            int variable = Integer.parseInt(teclado.nextLine().trim());
            int original = variable;

            // FR1: crear el proceso hijo
            ProcessBuilder pb = new ProcessBuilder(
                    System.getProperty("java.home") + java.io.File.separator + "bin" + java.io.File.separator + "java",
                    "-cp", System.getProperty("java.class.path"),
                    Tarea1.class.getName(), String.valueOf(original));
            pb.inheritIO();
            Process hijo = pb.start();

            variable = variable - 5;                              // FR3: el padre resta 5

            int codigo = hijo.waitFor();                          // esperar al hijo
            if (codigo != 0) {
                System.err.println("Error: el hijo termino con codigo " + codigo);
                return;
            }

            // FR5: mostrar todos los valores
            System.out.println("[PADRE] Valor tras restar 5: " + variable);
            System.out.println("Resumen -> original: " + original
                    + " | padre (-5): " + variable
                    + " | hijo (+4): " + (original + 4));

        } catch (NumberFormatException e) {
            System.err.println("Error: no has introducido un numero entero valido");
        } catch (java.util.NoSuchElementException e) {
            System.err.println("Error: no se ha introducido ningun valor");
        } catch (java.io.IOException e) {
            System.err.println("Error al crear el proceso hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error: se interrumpio la espera del hijo");
        }
    }
}