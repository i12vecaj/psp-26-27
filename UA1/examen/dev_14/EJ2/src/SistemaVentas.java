// Examen UA1 EJ2 SistemaVentas - Federico Lora

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SistemaVentas {

    public static void main(String[] args) {
        int aforo = 200;
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];

        // lanza las cinco taquillas a la vez
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(cupos[i]);
            taquillas[i].start();
        }

        // espera a que acaben todas
        for (int i = 0; i < taquillas.length; i++) {
            try {
                taquillas[i].join();
            } catch (InterruptedException e) {
                System.err.println("Se corto la espera de la taquilla " + (i + 1));
            }
        }
        long tiempo = System.currentTimeMillis() - inicio;

        // ya han acabado y se lee lo de cada una
        int total = 0;
        for (int i = 0; i < taquillas.length; i++) {
            int vendidas = taquillas[i].getVendidas();
            System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
            total += vendidas;
        }
        System.out.println("Total vendido: " + total + " de " + aforo);
        System.out.println("Tiempo de ventas: " + tiempo + " ms");

        // lanza el auditor y le manda el aforo y el total
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Auditor");
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();

            OutputStream os = p.getOutputStream();
            os.write((aforo + "\n" + total + "\n").getBytes());
            os.flush();
            os.close();

            // enseña lo que contesta
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();

            int codigo = p.waitFor();
            System.out.println("Codigo del auditor: " + codigo);
        } catch (IOException e) {
            System.err.println("No se pudo lanzar el auditor");
        } catch (InterruptedException e) {
            System.err.println("Se corto la espera del auditor");
        }
    }
}
