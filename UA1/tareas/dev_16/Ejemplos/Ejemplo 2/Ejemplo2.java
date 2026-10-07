package com.ceslopedevega.procesos;
import java.io.*;
import java.nio.charset.Charset;

public class Ejemplo2 {
    public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();
        try {

            // CMD escribe con la codificación de consola de Windows (CP850 en español)
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), Charset.forName("CP850")));

            // mostramos en pantalla línea a línea, numerándolas
            String linea;
            int numLinea = 1;
            while ((linea = br.readLine()) != null) {
                System.out.println(numLinea + ": " + linea);
                numLinea++;
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
