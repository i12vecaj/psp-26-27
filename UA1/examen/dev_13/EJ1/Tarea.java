import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tarea {
    static void main(String[] args) throws IOException {

        // Nos dice sino se han introducido todos los argumentos necesarios
        if (args.length > 2){
            System.err.println(" Error: Faltan argumentos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos = 0;

        // Nos obliga a ingresar un numero entero
        try{
            segundos = Integer.parseInt(args[1]);
        }catch (NumberFormatException e ) {
            System.out.println("Error: El segundo argumento debe ser un numero entero");
            System.exit(2);
        }
        // Nos obliga a ingresar un numero que no se anegativo
        if (segundos < 0){
            System.out.println("Error: El numero no puede ser negativo");
            System.exit(3);
        }
        // Nos indica que el mensaje ha sido recibido correctamente
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ){
            String mensaje = reader.readLine();
            if (mensaje != null){
                System.out.println(nombre + " mensaje recibido" + mensaje);
            }
        // Nos muestra error si la lectura no es correcta
        }catch (IOException e){
            System.err.println(nombre + "Error al leer");
        }
        // Si el nonmbre coincide con "falla" lanza una excepcion de que coincide el nombre
        if ("falla".equals(nombre)){
            System.err.println("Falla error simulado");
            System.exit(4);
        }
        // Indica que ha termindao el proceso
        try {
            System.out.println(nombre + "trabajando" + segundos);
            Thread.sleep(segundos * 1000L);
            System.out.println(nombre + "terminado");
            System.exit(0);
        }catch (InterruptedException e ){
            Thread.currentThread().interrupt();
            System.exit(5);
        }
    }
}
