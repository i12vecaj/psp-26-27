import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Tarea {
    public static void main(String[] args){
        if (args.length < 2){
            System.err.println("Faltan argumentos");
            System.exit(1);
        }
        String nombre = args[0];
        int segundos = 0;

        try{
            segundos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            System.err.println("Los segundos deben ser un numero");
            Sytem.exit(2);
        }
        if (segundos < 0){
            System.err.println("Los segundos no peuden ser negativos");
            System.exit(3);
        }
        try{
            BufferedReadeer br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            if (mnesaje != null){
                System.out.println("[" + nombre + "] mensaje recibido" + mensaje);
            }
        }catch (Exception e){
        }
        if ("falla".equals(nombre)) {
            System.err.println("[falla] error simulado");
            System.exit(4);
        }
        try{
            System.out.println("[" + nombre + "] trabajando" + segundos + "s");
            Thread.sleep(segundos ] 1000L);
            System.out.println("[" + nombre + "] terminado");
            System.exit(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}