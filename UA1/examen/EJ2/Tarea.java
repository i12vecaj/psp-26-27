import java.util.Scanner;
public class Tarea {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Faltan datos de la tarea");
            System.exit(1);
        }
    
    String nombre= args[0];
    int segundos=0;
    try{
         segundos = Integer.parseInt(args[1]);}catch(NumberFormatException e){
            System.err.println("["+nombre+"] los segundos no son un número: ");
            System.exit(2);
        }
   
    if (segundos < 0) {
        System.err.println("El tiempo de la tarea no puede ser negativo");
        System.exit(3);
    }

    Scanner sc = new Scanner(System.in);
    String msg= sc.nextLine();
    System.out.println("["+nombre+"] Mensaje recibido: "+msg);

    if (nombre.trim().equalsIgnoreCase("falla")) {
        System.err.println("[falla] error simulado");
        System.exit(4);
    }

    System.out.println("["+nombre+"] trabajando " + segundos + " segundos");
    try {
        Thread.sleep(segundos *1000);
    } catch (InterruptedException e) {
        System.err.println("["+nombre+"] la tarea ha sido interrumpida");
    }
    System.out.println("["+nombre+"] terminado");
    System.exit(0);
}}

