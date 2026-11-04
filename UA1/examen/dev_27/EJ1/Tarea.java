package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Tarea {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);
        }

        String nom = args[0];
        int seg = 0;

        try {
            seg = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("No es un numero");
            System.exit(2);
        }

        if (seg < 0) {
            System.err.println("Segundos negativos");
            System.exit(3);
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = br.readLine();
            if (msg != null) {
                System.out.println("[" + nom + "] mensaje recibido: " + msg);
            }
        } catch (Exception e) {}

        if (nom.equals("falla")) {
            System.err.println("[" + nom + "] error simulado");
            System.exit(4);
        }

        try {
            System.out.println("[" + nom + "] trabajando " + seg + " s");
            Thread.sleep(seg * 1000L);
            System.out.println("[" + nom + "] terminado");
            System.exit(0);
        } catch (InterruptedException e) {
            System.exit(5);
        }
    }
}