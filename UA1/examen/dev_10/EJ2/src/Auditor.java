import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso independiente que comprueba si el total vendido coincide con el aforo.
 * Entrada estandar: linea 1 = aforo, linea 2 = total vendido.
 * Codigos de salida: 0 coincide, 1 total mayor, 2 total menor, 3 datos no validos.
 */
public class Auditor {

    private static final int COINCIDE = 0;
    private static final int MAYOR = 1;
    private static final int MENOR = 2;
    private static final int DATOS_NO_VALIDOS = 3;

    public static void main(String[] args) {
        int aforo;
        int total;
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
            aforo = leerNumero(entrada.readLine());
            total = leerNumero(entrada.readLine());
        } catch (IOException | IllegalArgumentException e) { // NumberFormatException incluida
            System.out.println("Auditor: datos no validos");
            System.exit(DATOS_NO_VALIDOS);
            return;
        }

        if (aforo <= 0 || total < 0) { // regla P4: valores fuera de rango
            System.out.println("Auditor: datos no validos");
            System.exit(DATOS_NO_VALIDOS);
        } else if (total == aforo) {
            System.out.println("Auditor: correcto, se han vendido " + total + " entradas de un aforo de " + aforo);
            System.exit(COINCIDE);
        } else if (total > aforo) {
            System.out.println("Auditor: error, se han vendido " + total + " entradas, mas que el aforo de " + aforo);
            System.exit(MAYOR);
        } else {
            System.out.println("Auditor: error, se han vendido " + total + " entradas, menos que el aforo de " + aforo);
            System.exit(MENOR);
        }
    }

    private static final char BOM = '﻿';

    /** Convierte una linea en entero; lanza IllegalArgumentException si falta o no es numerica. */
    private static int leerNumero(String linea) {
        if (linea == null) {
            throw new IllegalArgumentException("Falta un dato");
        }
        // Algunos emisores (p. ej. PowerShell 5.1) anteponen un BOM UTF-8 que trim() no elimina
        if (!linea.isEmpty() && linea.charAt(0) == BOM) {
            linea = linea.substring(1);
        }
        return Integer.parseInt(linea.trim());
    }
}
