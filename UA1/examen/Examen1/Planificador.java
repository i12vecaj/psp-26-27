import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Planificador {

    static class TareaLanzada{
        String nombre;
        Process proceso;
        long inicio;

        public TareaLanzada(String nombre, Process proceso, long inicio) {
            this.nombre = nombre;
            this.proceso = proceso;
            this.inicio = inicio;
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String tareas[][] = {
                {"A", "2"},
                {"B", "1"},
                {"C", "8"},
                {"falla", "1"},
                {"D", "-1"}
        };

        List<TareaLanzada> procesos = new ArrayList<>();

        int ok = 0;
        int error = 0;
        int timeout = 0;

        try{
            for (String[] tarea : tareas){

                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "-cp",
                        System.getProperty("java.class.path"),
                        tarea[0],
                        tarea[1]
                );

                pb.redirectError(
                        ProcessBuilder.Redirect.appendTo(
                                new File("errores.log")
                        )
                );

                Process p = pb.start();

                procesos.add(
                        new TareaLanzada(tarea[0], p)
                );
            }

            for (TareaLanzada t: procesos){

                try (BufferedWriter bw =
                             new BufferedWriter(
                                     new OutputStreamWriter(
                                             t.proceso.getOutputStream()
                                     ))) {

                    bw.write(
                            "Hola desde el padre a " + t.nombre
                    );

                    bw.newLine();
                }catch (IOException e){
                    //El hijo ya ha terminado
                }
            }

        } catch (InterruptedException e) {
            System.out.println("");
        }
    }
}
