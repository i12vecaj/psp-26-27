
import java.io.*;
public class Tarea {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Faltan argumentos");
            System.exit(1);
        }
        String nombre = args[0];
        int segundos = 0;
        try {
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error número no valido");
            System.exit(2);
        }
        if (segundos < 0) {
            System.out.println("Error: Los segundos proporcionados son negativos");
            System.exit(3);
        }
        String mensaje = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            mensaje = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nombre + "mensaje recibido: " + mensaje);

        if ("falla".equalsIgnoreCase(nombre)) {
            System.err.println("falla error simulado");
            System.exit(4);
        }
        System.out.println(nombre + "trabajando " + segundos + " s");
        try {
            Thread.sleep(segundos * 1000L);
        }catch (InterruptedException e){
            System.out.println(nombre + "Interrumpido");
        }
        System.out.println(nombre+" terminado");
        System.exit(0);
    }


}