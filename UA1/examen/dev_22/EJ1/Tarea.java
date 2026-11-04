
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Tarea {

    public static void main(String[] args) {

        // Comprobamos que hay dos argumentos
        if (args.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos;

        // Comprobamos que los segundos son un numero
        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Los segundos no son un numero");
            System.exit(2);
            return;
        }

        // Comprobamos que los segundos no son negativos
        if (segundos < 0) {
            System.err.println("Los segundos son negativos");
            System.exit(3);
        }

        // Leemos el mensaje que envia el padre
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));

            String mensaje = br.readLine();

            System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);

        } catch (IOException e) {
            System.err.println("Error al leer el mensaje");
            System.exit(5);
        }

        // Simulamos un error si el nombre es falla
        if (nombre.equals("falla")) {
            System.err.println("[falla] error simulado");
            System.exit(4);
        }

        // Realizamos el trabajo
        System.out.println("[" + nombre + "] trabajando " + segundos + " s");

        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            System.err.println("Tarea interrumpida");
            Thread.currentThread().interrupt();
            System.exit(5);
        }

        System.out.println("[" + nombre + "] terminado");

        System.exit(0);
    }
}
