import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Tarea {
    public static void main(String[] args) {
        // Comprobar que hay dos argumentos
        if (args.length < 2) {
            System.err.println("Faltan argumentos: nombre segundos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos = 0;

        // Comprobar que los segundos son un número
        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Los segundos no son un numero");
            System.exit(2);
        }

        // Comprobar que no negativos
        if (segundos < 0) {
            System.err.println("Los segundos son negativos");
            System.exit(3);
        }

        // Leer el mensaje del padre
        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = lector.readLine();
            System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);
        } catch (Exception e) {
            System.err.println("Error leyendo el mensaje");
        }

        // Simulacion de error
        if (nombre.equals("falla")) {
            System.err.println("[falla] error simulado");
            System.exit(4);
        }

        // Trabajo normal
        System.out.println("[" + nombre + "] trabajando " + segundos + " s");
        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            System.err.println("Interrumpida");
        }
        System.out.println("[" + nombre + "] terminado");
        System.exit(0);
    }
}