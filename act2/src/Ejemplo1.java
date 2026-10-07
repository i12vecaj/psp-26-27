package com.ceslopedevega.procesos;

import java.io.IOException;

public class Ejemplo1 {
    public static void main(String[] args) throws IOException {
        // Creamos el ProcessBuilder para abrir el Explorador de Windows
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");

        // Puedes cambiar el bucle si quieres abrir más de una ventana
        for (int i = 0; i < 1; i++) {
            Process p = pb.start();
            System.out.println("Proceso lanzado correctamente: " + (i + 1));
        }
    }
}