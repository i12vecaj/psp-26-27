package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {

            ProcessBuilder pb = new ProcessBuilder("java", "-version");
            pb.redirectErrorStream(true);
            Process proceso = pb.start();


            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            System.out.println("Salida del Proceso");
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }

            int codigoSalida = proceso.waitFor();
            System.out.println("Código de salida: " + codigoSalida);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
