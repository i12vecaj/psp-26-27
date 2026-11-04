import java.io.File;
import java.io.IOException;

public class Planificador {
    static void main(String[] args) {
        String[][] tareasArgs ={
                {"A","2"},
                {"B","1"},
                {"C","8"},
                {"falla","1"},
                {"D","-1"}
        };

        Process[] procesos = new Process[tareasArgs.length];
        String[] nombres = new String[tareasArgs.length];

        for (int i =0; i < tareasArgs.length; i++){
            nombres[i] = tareasArgs[i][0];

            ProcessBuilder pb = new ProcessBuilder("java"," -cp", System.getProperty("java.class.path")," tarea", nombres[i], tareasArgs[i][1]);
            pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores.log")));

            try{
                procesos[i] = pb.start();
            }catch (IOException e){
                System.out.println();
            }
        }

    }

}
