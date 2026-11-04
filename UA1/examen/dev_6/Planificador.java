package UA1.examen.dev_6;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class Planificador {

    public static void main(String[] args){
        // Datos de las tareas
        String[] nombres = {"Tarea1", "Tarea2", "Tarea3", "Tarea1911"};
        String[] segundos = {"1", "2", "3", "7", "-1"};
        Process[] procesos = new Process[nombres.length];

        File logErrores = new File("errores.log");
        String cp = System.getProperty("Planificador.java");

        // Lanzamos los procesos a la vez
        for (int i = 0; i < nombres.length; i++){
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp,"Tarea",nombres[i],segundos[i] );

            //Redirigo los errores a errores.log
            pb.redirectError(ProcessBuilder.Redirect.appendTo(logErrores));

            try {
                procesos[i] = pb.start();

                //Le envio una señal al proceso hijo y cierro flujo para que no se quede esperando
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(procesos[i].getOutputStream()))) {
                    bw.write("Hola desde padre a " + nombres[i]);
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException e){
                System.err.println("Error al lanzar la tarea: " + nombres[i]);
            }
        }

        int totalok = 0;
        int totalError = 0;
        int totalTout = 0;

        //Compruebo el estado de cada proceso con 3 segundos de maximo de espera
        for (int i = 0; i < nombres.length; i++){
            Process p = procesos[i];
            String nombre = nombres[i];

            if (p == null) continue;

            try{
                boolean terminado = p.waitFor(3,TimeUnit.SECONDS);

                if (!terminado){
                    //Si pasan 3 segundos lo mato y lo cuento para timeout
                    p.destroyForcibly();
                    System.out.println("Tarea " + nombre + ": Timeout (cancelada)");
                    totalTout ++;
                }else {
                    int codigo = p.exitValue();
                    if(codigo == 0){
                        System.out.println("Tarea " + nombre + ": OK (codigo 0 )");
                        totalok ++;
                    }else {
                        System.out.println("Tarea " + nombre + ": Error (codigo" + codigo + ")");
                        totalError ++;
                    }
                }
                //Muestro la salida del proceso si no se ha cancelado
                try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                    String linea;
                    while ((linea = br.readLine()) != null){
                        System.out.println("    " + linea);
                    }
                }
            } catch (InterruptedException e){
                System.err.println("Espera interrumpida en tarea: " + nombre);
            } catch (IOException e){
                System.err.println("Error leyendo la salida de la tarea: " + nombre);
            }
        }
        System.out.println("Resumen: " + totalok + " ok, " + totalError + " ERROR, " + totalTout + " Timeout ");
    }
}
