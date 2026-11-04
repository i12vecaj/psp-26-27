import java.util.Scanner;

public class Tarea {

    public static void main(String[] args) {

        // Comprobar argumentos
        if (args.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos;

        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error en los segundos");
            System.exit(2);
            return;
        }

        if (segundos < 0) {
            System.err.println("Segundos negativos");
            System.exit(3);
        }

        // Leer mensaje del padre
        Scanner sc = new Scanner(System.in);
        String mensaje = sc.nextLine();

        System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);

        // Simular error
        if (nombre.equals("falla")) {
            System.err.println("[falla] error simulado");
            System.exit(4);
        }

        // Esperar los segundos
        System.out.println("[" + nombre + "] trabajando " + segundos + " s");

        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            System.err.println("Interrumpido");
            System.exit(5);
        }

        System.out.println("[" + nombre + "] terminado");
        System.exit(0);
    }
}