import java.util.Scanner;

public class Tarea {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String nombre = args[0];
        int nseg = 0;

        if(args.length < 2){
            System.err.println("Error, faltan argumentos");
            System.exit(1);
        }

        for (int i = 0; i < args.length; i++) {
            boolean esNumero = true;

            for (int j = 0; j < args[i].length(); j++){
                if(Character.isDigit(args[i].charAt(j))){
                    esNumero = false;
                }
            }
            if (nseg < 0) {
                System.err.println("Error, los segundos no pueden ser negativos");
                System.exit(3);
            } else if (Integer.parseInt(args[i]) < 0) {
                System.err.println("Error, deben ser números");
                System.exit(2);
            }
        }
        System.out.println("Todo está bien");

        //Mensaje que manda el padre
        String mensaje = sc.nextLine();
        if(nombre.equals("falla")){
            System.err.println("[falla] Error simulado");
            System.exit(4);
        }

        System.out.println("[" + nombre + "] trabajando " + nseg + "s");

        try{
            Thread.sleep(nseg * 1000L);
        }catch(InterruptedException e){
            System.err.println("Conexión interrumpida");
        }

        System.out.println("["+nombre+"] terminado");
        System.exit(0);
    }
}
