import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//proceso hijo

public class Tarea {
    public static void main(String[] args) {

        //se comprurban los argumentos
        if (args.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);

        }

        String nombre = args[0];
        int segundos;

        try {
            segundos = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {
            System.err.println("Los segundos nos es un numero.");
            System.exit(2);
            return;

        }

        // segundos en positivo
        if (segundos < 0) {
            System.err.println("Los segundos no pueden ser negativos");
            System.exit(3);

        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();

            System.out.println(nombre + " : mensaje recibido: " + mensaje);

            System.out.println(nombre + " : trabajando " + segundos + " segundos.");

            Thread.sleep(segundos * 1000L);

            System.out.println(nombre + " terminado.");

            System.exit(0);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(99);
        }

    }
}
