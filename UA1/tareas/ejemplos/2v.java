package com.ceslopedevega.procesos;
import java.io.*;

public class Ejemplo2 {
	public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream()))) {

            String linea;
            int numero = 1;

            while ((linea = reader.readLine()) != null) {
                System.out.println(numero + ": " + linea);
                numero++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Comprobación de error: 0 = correcto; otro valor = error
        try {
            int exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}//Ejemplo2