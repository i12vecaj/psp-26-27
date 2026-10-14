/*
 * T2 - Programacion de procesos en Java (I)
 *
 * Segundo programa: lanza el LectorCadena como un proceso aparte con
 * ProcessBuilder y espera a que acabe.
 *
 * javac LectorCadena.java EjecutorLector.java
 * java EjecutorLector                 -> el texto se escribe por teclado
 * java EjecutorLector "hola mundo*"   -> el texto se lo paso yo al hijo
 */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;

public class EjecutorLector {

    public static void main(String[] args) {

        // busco el java del jre que estoy usando y reaprovecho el classpath
        // actual, asi encuentra LectorCadena.class sin configurar nada
        String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "LectorCadena");

        boolean textoEnArgumentos = args.length > 0;

        if (textoEnArgumentos) {
            // solo redirijo la salida, porque la entrada la escribo yo
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        } else {
            // el hijo usa la misma consola que yo y el usuario le escribe
            pb.inheritIO();
        }

        System.out.println("Lanzando LectorCadena...\n");

        Process hijo;

        try {
            hijo = pb.start();
        } catch (IOException e) {
            // suele pasar si falta LectorCadena.class o no encuentra el java
            System.out.println("No se ha podido arrancar el proceso: " + e.getMessage());
            return;
        }

        if (textoEnArgumentos) {
            // al cerrar el writer el hijo recibe el fin de entrada
            try (Writer hacia = new OutputStreamWriter(hijo.getOutputStream())) {
                hacia.write(String.join(" ", args));
            } catch (IOException e) {
                System.out.println("No se ha podido enviar el texto al hijo: " + e.getMessage());
            }
        }

        try {
            int codigo = hijo.waitFor();
            System.out.println("\nEl hijo ha terminado con codigo " + codigo);
            if (codigo != 0) {
                System.out.println("El hijo ha dado error.");
            }
        } catch (InterruptedException e) {
            hijo.destroy();
            Thread.currentThread().interrupt();
            System.out.println("Se ha interrumpido la espera del hijo.");
        }
    }
}
