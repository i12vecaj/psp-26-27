// Examen UA1 EJ1 Tarea - Federico Lora

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tarea {

    public static void main(String[] args) {
        // sin argumentos sale con 1
        if (args.length < 2) {
            System.err.println("Faltan el nombre y los segundos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos = 0;

        // si no es un numero sale con 2
        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("[" + nombre + "] los segundos no son un numero");
            System.exit(2);
        }

        // si es negativo sale con 3
        if (segundos < 0) {
            System.err.println("[" + nombre + "] los segundos son negativos");
            System.exit(3);
        }

        // lee el mensaje del padre
        String mensaje = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            mensaje = br.readLine();
        } catch (IOException e) {
            System.err.println("[" + nombre + "] no se pudo leer el mensaje");
        }
        System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);

        // falla sale con 4
        if (nombre.equals("falla")) {
            System.err.println("[falla] error simulado");
            System.exit(4);
        }

        // espera los segundos
        System.out.println("[" + nombre + "] trabajando " + segundos + " s");
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            System.err.println("[" + nombre + "] se corto la espera");
        }
        System.out.println("[" + nombre + "] terminado");
        System.exit(0);
    }
}
