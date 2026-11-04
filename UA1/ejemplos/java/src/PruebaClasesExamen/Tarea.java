

public class Tarea {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos;

        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Los segundos no son un número");
            System.exit(2);
            return;
        }

        if (segundos < 0) {
            System.err.println("Los segundos no pueden ser negativos");
            System.exit(3);
        }

        try {
            java.util.Scanner sc = new java.util.Scanner(System.in);
            String mensaje = sc.nextLine();

            System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);

            if (nombre.equals("falla")) {
                System.err.println("[falla] error simulado");
                System.exit(4);
            }

            System.out.println("[" + nombre + "] trabajando " + segundos + " s");

            Thread.sleep(segundos * 1000L);

            System.out.println("[" + nombre + "] terminado");

            System.exit(0);

        } catch (Exception e) {
            System.err.println("Error en la tarea: " + e.getMessage());
            System.exit(5);
        }
    }
}
