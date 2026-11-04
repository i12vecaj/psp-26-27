import java.util.Scanner;

public class Tarea {
    //FR1 (0,75 puntos): Tarea recibe dos argumentos: un nombre y un número de segundos.
    public static void main(String[] args) {
        if (args.length != 2){
            System.err.println("Se necesitan 2 argumentos");
            System.exit(1);

        }
        String nombre = args[0];
        int segundos = 0;
        try{
            segundos = Integer.parseInt(args[1]);
            if (segundos < 0){
                System.err.println("Error: Segundos negativos");
                System.exit(3);
            }
        }catch (NumberFormatException e){
            System.err.println("Error");
            System.exit(2);
        }
        Scanner sc = new Scanner(System.in);
        String mensajeParaPadre = sc.nextLine();
        System.out.println(nombre + "mensaje recibido " + mensajeParaPadre);
        if (nombre.equals("falla")){
            System.err.println("[FALLA] error simulado");
            System.exit(4);
        }
        System.out.println(nombre + "trabajando " + segundos + " s");
        try{
            Thread.sleep(segundos * 1000);
        }catch (InterruptedException e){
            System.err.println("Espera interrumpida");
        }

        System.out.println(nombre + "termiando");
        System.exit(0);


    }
}
