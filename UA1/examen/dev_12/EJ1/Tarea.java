import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Tarea {
    static void main(String[] args) {
        //comprobar número de argumentos
        if (args.length < 2){
            System.out.println("Faltan argumentos");
            System.exit(1);
        }

        String nombre = args[0];
        int segundos;

        //comprobar que los segundos sean números
        try{
           segundos = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("Error, los segundos tienen que ser números");
            System.exit(2);
            return;
        }

        //comprobar que los segundos no sean negativos
        if (segundos < 0){
            System.out.println("Error, los segundos son negativos");
            System.exit(3);
        }


        try{
            //leer una línea enviada por el padre
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String mensaje = br.readLine();

            System.out.println("["+nombre+"} mensaje recibido: "+mensaje);

            //error simulado
            if ("falla".equals(nombre)){
                System.err.println("[Falla] error simulado");
                System.exit(4);
            }

            System.out.println("["+nombre+"] trabajando "+segundos+" s");

            System.out.println("["+nombre+"] terminado");
            System.exit(0);

        }catch (Exception e){
            System.err.println("Error: "+e.getMessage());
        }
    }
}
