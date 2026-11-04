import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Planificador {
    public static void main(String[] args) {
      String nombres[] = {"A", "B", "C", "falla", "D"};
      String segundos[] = {"2", "1", "8", "1", "-1"};
      Process[] procesos = new Process[nombres.length];

      File errores = new File("errores.log");
      for (int i = 0; i < nombres.length; i++) {
          
              ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea", nombres[i], segundos[i]);
              pb.redirectError(ProcessBuilder.Redirect.appendTo(errores));
             
              try {
                  procesos[i] = pb.start();
                  PrintWriter pw = new PrintWriter(procesos[i].getOutputStream());
                  pw.println("hola desde el padre a " + nombres[i]);
                  pw.close();
              } catch (IOException e) {
                  System.err.println("No se ha podido lanzar la tarea " + nombres[i]);
              }
          }
          int ok = 0;
          int error = 0;
          int timeout = 0;

          for (int i=0; i< nombres.length; i++) {
              if(procesos[i] != null) {
                 error++;
                 continue;
              }
              try {
                  boolean terminado = procesos[i].waitFor(3, TimeUnit.SECONDS);
                  if(terminado) {
                     procesos[i].destroy();
                     System.out.println("Tarea " + nombres[i] + " Timeout cancelada");
                     timeout++;
                  } else {
                    int codigo = procesos[i].exitValue();
                    if(codigo == 0) {
                        System.out.println("Tarea " + nombres[i] + ": ok (codigo " + codigo + ")");
                        ok++;
                    } else {
                        System.out.println("Tarea " + nombres[i] + ": error (codigo " + codigo + ")");
                        error++;
                    }
                Scanner sc = new Scanner(procesos[i].getInputStream());
                while(sc.hasNextLine()) {
                    System.out.println("[" + nombres[i] + "] " + sc.nextLine());
                }
                sc.close();
              }

          }catch (InterruptedException e) {
              System.err.println("Se ha interrumpido la espera de la tarea " + nombres[i]);
              procesos[i].destroy();
          }
      }
System.out.println("Resumen: " + "OK, "+ ok + " tareas correctas, " + error + " tareas con error, " + timeout + " tareas canceladas por timeout");
  }
}
