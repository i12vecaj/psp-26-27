
public class Tarea {

    public static void main(String[] args) {

        // comprobamos que se hayan pasado los dos argumentos
        if (args.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos;

        // convertimos los segundos a entero
        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Los segundos no son un número");
            System.exit(2);
            return;
        }

        // comprobamos que los segundos no sean negativos
        if (segundos < 0) {
            System.err.println("Los segundos no pueden ser negativos");
            System.exit(3);
        }

        try {
            // leemos el mensaje que nos manda el proceso padre
            java.util.Scanner sc = new java.util.Scanner(System.in);
            String mensaje = sc.nextLine();

            System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);

            // si el nombre es falla, simulamos un error
            if (nombre.equals("falla")) {
                System.err.println("[falla] error simulado");
                System.exit(4);
            }

            // mostramos el tiempo que va a trabajar la tarea
            System.out.println("[" + nombre + "] trabajando " + segundos + " s");

            // esperamos los segundos indicados
            Thread.sleep(segundos * 1000L);

            System.out.println("[" + nombre + "] terminado");

            System.exit(0);

        } catch (Exception e) {
            System.err.println("Error en la tarea: " + e.getMessage());
            System.exit(5);
        }
    }
}
