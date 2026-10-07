package com.ceslopedevega.procesos;
import java.io.*;

public class Ejemplo2 {
    public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();
        try {

            InputStream is = p.getInputStream();

            // leemos por líneas y las numeramos
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea;
            int numero = 1;
            while ((linea = br.readLine()) != null) {
                System.out.println(numero + ": " + linea);
                numero++;
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
