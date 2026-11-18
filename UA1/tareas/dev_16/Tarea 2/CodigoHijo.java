package com.ceslopedevega.procesos;

import java.io.*;

public class CodigoHijo {

    public static void main(String[] args) {

        InputStreamReader in = new InputStreamReader(System.in);
        StringBuilder texto = new StringBuilder();
        int c = -1;

        try {
            while ((c = in.read()) != -1 && c != '*') {
                texto.append((char) c);
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Error al leer la entrada estándar: " + e.getMessage());
            System.exit(2);
        }

        if (c == -1) {
            System.err.println("No se ha recibido el carácter de terminación '*'");
            System.exit(1);
        }

        System.out.println("Texto leído: " + texto);
        System.exit(0);
    }
}