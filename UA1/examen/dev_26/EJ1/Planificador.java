import java.io.BufferedReader;
import java.io.File;
import java.io.InputSreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Planificador {
    //defino los datos de las 5 tareas en arrays paralelos
    public static void main(String[] args){
        String[] nombres = {"A", "B", "C", "falla", "D"};
        String[] segundos = {"2", "1", "8", "1", "-1"};

        Process[] procesos = new Process[5]
        BufferedReader[] lectores = new BufferedReader[5];
        File ficheroErrores = new File("erroes.log");

        for (int i = 0; i < 5; i++){
            try{
                ProcessBuilder pb =  new ProcessBuilder(
                        "java", "-cp", System.getProperty("java.class.path"), "tarea", nombres[i], segundos[i]
                );
                // redirigir errores al log sin leerlos desde java
                pb.redirectError(ProcessBuilder.Redirect.appendTo(ficheroErrores));

                Process p = pb.start();
                procesos[i] = p;

                OutputStream os = p.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.println("Hola desde el padre a" + nombres[i]);
                pw.close();

                //guarda lector para su salida normal

                lectores[i] = new BufferedReader(new InputStreamReader(p.getInputStream()));
            } catch (Exception e) {
                System.err.println("Error al lanzar la tarea" + nombres[i]);
            }
        }
        int ok = 0;
        int erroe = 0;
        int timeout = 0;
        //esperar maximo 3 segundillos para cada una y comprobar el estado
        for (int i = 0; i<5; i++){
            boolean terminado = false;
            try{
                terminado = procesos[i].waitFor(3, TimeUnit.SECONDS);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}