package com.ceslopedevega.hilos;

/*

Realiza un segundo programa Java que ejecute al anterior.
Este programa deberá mostrar por pantalla lo que sucede según el valor devuelto al
 ejecutar el programa principal.

 Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

FR1: admita argumentos desde main() - 1 punto
FR2: devuelva con System.exit() los siguientes valores:
Si el número de argumentos es < 1 debe devolver 1 - 1 punto
Si el argumento es una cadena debe devolver - 1 punto
Si el argumento es un número entero menor que 0 debe devolver 3 - 1 punto
En cualquier otro caso debe devolver 0 - 1 punto
FR3: Crea después otro programa que ejecute el anterior - 1 punto
Implementa el control de errores - 2 puntos
Documenta el código - 2 puntos

*/

import java.io.IOException;
import java.util.Scanner;

public class Lanzador {

    public static void main(String[] args){
        try {

            ProcessBuilder pb;

            Scanner teclado = new Scanner(System.in);
            System.out.print("Introduce un valor: ");
            String dato = teclado.nextLine();

            if(args.length == 0){
                pb = new ProcessBuilder("java", "com.ceslopevega.com.hilos.Hilos", dato);
            }else{
                pb = new ProcessBuilder("java","Hilos", args[0]);
            }

            Process proceso = pb.start();

            int codigo = proceso.waitFor();

            switch (codigo){
                case 1:
                    System.out.println("No se proporciono ningun argumento");
                    break;
                case 2:
                    System.out.println("El argumento es una cadena no un numero");
                    break;
                case 3:
                    System.out.println("El argumento es un numero negativo");
                    break;
                case 0:
                    System.out.println("El argumento es un numero");
                    break;
            }


        } catch (IOException e) {
            System.out.println("No se pudo iniciar el programa Hilos: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Se interrumpió la espera del proceso.");
        }
    }

}
