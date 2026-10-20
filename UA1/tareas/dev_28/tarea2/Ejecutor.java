import java.io.IOException;

/**

 */
public class Ejecutor {

    public static void main(String[] args) {
        // FR3: Preparamos el comando para llamar al otro programa ya compilado
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "./src", "LectorCadena");

        // Redirigimos la entrada y salida para poder escribir y ver los datos en esta misma consola
        pb.inheritIO();

        try {
            System.out.println("Arrancando el programa LectorCadena...\n");

            // Arrancamos el proceso hijo
            Process proceso = pb.start();

            // Hacemos que el programa principal espere hasta que el LectorCadena termine
            int salida = proceso.waitFor();

            System.out.println("\nEl proceso ha terminado con el código: " + salida);

        } catch (IOException e) {
            // Fallo típico si intentas ejecutar y no encuentra el archivo .class
            System.err.println("Error al lanzar el proceso. ¿Te has acordado de compilar LectorCadena.java?");
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Por si cerramos el programa de golpe mientras está esperando
            System.err.println("Se interrumpió la ejecución del programa.");
            Thread.currentThread().interrupt();
        }
    }
}