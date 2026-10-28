package reto1;

import java.io.IOException;

public class Reto1 {
    public static void main(String[] args)throws IOException, InterruptedException {
        Process p = new ProcessBuilder("java","-version").start();


        System.out.println("Salida estandar:");
        System.out.println(new String(p.getInputStream().readAllBytes()));

        System.out.println("Salida Error:");
        System.out.println(new String(p.getErrorStream().readAllBytes()));

        int codigo = p.waitFor();
        System.out.println("Código de salida: " + codigo);

        //Ampliacion: lanza un comando que no exista y captura la excepción.

        try {
            Process pError = new ProcessBuilder("ComandoInexisitente").start();
        }catch (IOException e){
            System.out.println("Excepción al arrancar el comando: " + e.getMessage());
        }

    }
}
