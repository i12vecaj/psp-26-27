package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class PadreEco {
    public static void main(String[] args) {
         Process proceso = null;
           try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", System.getProperty("java.class.path"), "Eco");
            proceso = pb.start();
           PrintWriter escritor = new PrintWriter(proceso.getOutputStream(),true);
               BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
               escritor.println("hola");
               escritor.println("mundo");
               escritor.println("fin");
               escritor.close();
               String respuesta;
               System.out.println("Respuestas de Eco");
               while ((respuesta = lector.readLine()) !=null){
                   System.out.println(respuesta);
               }
               boolean terminado = proceso.waitFor(3, TimeUnit.SECONDS);
               if (!terminado) {
                   System.out.println("El proceso parece haberse congelado");
                   proceso.destroy();
               }else {
                   System.out.println("El proceso terminó correctamente");}


           } catch (Exception e) {
               
               throw new RuntimeException(e);
           }
    }
}
