package com.ceslopedevega.procesos;
import java.io.File;
import java.io.IOException;

public class Ejemplo8v2 {
    public static void main(String args[]) throws IOException {
        // Lanzamos el comando ls en Linux
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "ls");

        // En lugar de INHERIT (consola), redirigimos la salida a un archivo
        File archivoSalida = new File("salida_comando.txt");
        pb.redirectOutput(archivoSalida);

        Process p = pb.start();

        System.out.println("El comando se ha ejecutado. Revisa el archivo salida_comando.txt");
    }
}