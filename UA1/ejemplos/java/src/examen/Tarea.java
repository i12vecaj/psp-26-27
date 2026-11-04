package examen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
FR1 (0,75 puntos): Tarea recibe dos argumentos: un nombre y un número de segundos.
     - Si faltan argumentos termina con System.exit(1); si los segundos no son un número, con 2;
     si son negativos, con 3.
     En los tres casos escribe un mensaje por la salida de error.
     - Si los argumentos son correctos, lee UNA línea de su entrada estándar (el mensaje del padre) y escribe "[nombre] mensaje recibido: <mensaje>".
     - Si el nombre es "falla", escribe "[falla] error simulado" por la salida de error y termina con System.exit(4).
     - En el resto de casos escribe "[nombre] trabajando N s", espera esos segundos, escribe "[nombre] terminado" y termina con System.exit(0).
*/
public class Tarea {

    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length < 2){
            System.err.println("Error: Se requiere de nombre y segundos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos;

        try{
            segundos = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.err.println("Error: Los segundos deben de ser segundos");
            System.exit(2);
            return;
        }

        if(segundos < 0 ){
            System.err.println("Error: Los segundos deben un numero positivo");
            System.exit(3);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String mensaje = br.readLine();

        System.out.println("[" + nombre + "] mensaje recibido: " + mensaje);

        if("falla".equals(nombre)){
            System.err.println("[falla] error simulado");
            System.exit(4);
        }

        System.out.println("[" + nombre + "] trabajando " + segundos + " s");
        Thread.sleep(segundos * 1000);
        System.out.println("[" + nombre + "] terminado");

        System.exit(0);
    }

}
