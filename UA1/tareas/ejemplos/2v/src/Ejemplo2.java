package com.ceslopedevega.procesos;

import java.io.*;

public class Ejemplo2 {
    public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            int numLinea = 0;

            while ((linea = br.readLine()) != null) {
                numLinea++;
                System.out.println(numLinea + ": " + linea);
            }
            br.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        // COMPROBACION DE ERROR - 0 bien - 1 mal
        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}// Ejemplo2
