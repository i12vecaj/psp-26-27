import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Planificador {
    static void main(String[] args) {
        String[] arg = {"A,2","B,1","C,8","falla,1","D,-1"};
        Process[] procesos = new Process[arg.length];


        try(BufferedReader lector = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while((linea = lector.readLine());
        }


        int codigo = tarea.waitFor();
        switch (args.length) {
            case 0: System.out.println(String.format("Nombre recibido: "+ nombre));
            break;
            case 1:
                System.out.println(String.format("Faltan argumentos"));
                break;
            case 2:
                System.out.println(String.format("Los segundos no son un número"));
                break;
                case 3:
                System.out.println(String.format("Son negativos"));
                break;

        }
    }
}
// El switch para poner cada numero con su error para luego en los try/catch, si inclumple
//alguno se queda en ese y mostrará el mensaje correspondiente, si no, el mensaje de nombre recibido

//Arriba los strings completos
