package com.ceslopedevega.procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejemplo2 {
    public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {
                System.out.println(numeroLinea + ": " + linea);
                numeroLinea++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}