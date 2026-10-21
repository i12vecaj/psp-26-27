package com.ceslopedevega.hilos;

/*
PROCESOS. Crea un programa en Java que admita argumentos desde main() y
devuelva con System.exit() los siguientes valores:

Si el número de argumentos es < 1 debe devolver 1
Si el argumento es una cadena debe devolver 2
Si el argumento es un número entero menor que 0 debe devolver 3
En cualquier otro caso debe devolver 0

*/

public class Hilos {

    public static void main(String[] args){

        if(args.length < 1){
            System.exit(1);
        }

        try {
            int numero = Integer.parseInt(args[0]);

            if(numero < 0 ){
                System.exit(3);
            }

            System.exit(0);

        } catch (Exception e) {
            System.exit(2);
        }

    }

}
