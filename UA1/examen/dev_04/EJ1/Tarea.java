package EJ1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tarea {
    public static void main(String[] args) {
        //mira si faltan argumentos
        //nunca entenderé pq se comprueba primero y luego se ponen las variables, pero asi está en Hijo.java y si no da error
        if (args.length < 2) {
            System.err.println("Faltan argumentos: Tarea <nombre> <segundos>");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos = 0;

        // comprueba que son un numero
        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("No es un número: " + args[1]);
            System.exit(2);
        }

        // Comprueba que  no son negativos
        if (segundos < 0) {
            System.err.println("No pueden ser negativos: " + segundos);
            System.exit(3);
        }

        // Lee una linea de la entrada estandar (el mensaje del padre)
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = entrada.readLine();
            System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);
        } catch (IOException e) {
            System.err.println("[" + nombre + "] error al leer el mensaje");
            System.exit(6);
        }

        // Simula un fallo
        if (nombre.equals("falla")) {
            System.err.println("[falla] error simulado");
            System.exit(4);
        }


        System.out.println("[" + nombre + "] trabajando " + segundos + " s");
        try {
            Thread.sleep(segundos * 1000L); 
            //se pone L para que sea long y no int, pq si no da error y se pone asi pq son milisegundos
        } catch (InterruptedException e) {
            System.err.println("[" + nombre + "] espera interrumpida");
            System.exit(5);
        }
        System.out.println("[" + nombre + "] terminado");
        System.exit(0);
    }
}
