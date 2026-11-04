// Examen UA1 EJ2 Auditor - Federico Lora

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Auditor {

    public static void main(String[] args) {
        int aforo = 0;
        int total = 0;

        // lee el aforo y el total que le manda el padre
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            aforo = Integer.parseInt(br.readLine());
            total = Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Auditor: datos no validos");
            System.exit(3);
        }

        // si son negativos tampoco valen
        if (aforo <= 0 || total < 0) {
            System.out.println("Auditor: datos no validos");
            System.exit(3);
        }

        // compara el total con el aforo
        if (total == aforo) {
            System.out.println("Auditor: correcto, se han vendido " + total + " de " + aforo);
            System.exit(0);
        } else if (total > aforo) {
            System.out.println("Auditor: error, se han vendido " + total + " y el aforo es " + aforo);
            System.exit(1);
        } else {
            System.out.println("Auditor: error, faltan " + (aforo - total) + " entradas por vender");
            System.exit(2);
        }
    }
}
