import java.io.*;

public class Tarea {
    public static void main(String[] args) {

        // COMPROBACION DE ARGUMENTOS
        if (args.length < 1) {
            System.err.println("faltan argumentos: nombre y segundos");
            System.exit(1);
        }
        String nombre = args[0];
        int seg = 0;
        // se convierten los segundos a numero
        try {
            seg = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("[" + nombre + "] los segundos no son un numero: " + args[1]);
            System.exit(2);
        }

        // comprobacion de segundos negativos
        if (seg <= 0) {
            System.err.println("[" + nombre + "] los segundos son negativos: " + seg);
            System.exit(3);
        }

        try {
            // lectura obtiene el mensaje del padre
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // tarea que simula un error
            if (nombre.equals("falla")) {
                System.err.println("[falla] error simulado");
                System.exit(4);
            }
                String msg = br.readLine();
            System.out.println("[" + nombre + "] mensaje recibido: " + msg);

            // la tarea trabaja y termina
            System.out.println("[" + nombre + "] trabajando " + seg + " s");
            Thread.sleep(seg * 1000);
            System.out.println("[" + nombre + "] terminado");
        } catch (IOException e) {

            System.err.println("[" + nombre + "] error al leer el mensaje: " + e.getMessage());
                System.exit(5);
        } catch (InterruptedException e) {
            System.err.println("[" + nombre + "] interrumpida: " + e.getMessage());
            System.exit(5);
        }
        System.exit(0);
    }
}
