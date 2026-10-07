package com.ceslopedevega.procesos;
import java.io.IOException;

public class Ejemplo1 {
    public static void main(String[] args) throws IOException, InterruptedException  {
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
        System.out.println("PID padre (JVM): " + ProcessHandle.current().pid());
        for(int i=0; i<1;i++)
        {
            Process p = pb.start();
            System.out.println("PID hijo (explorer): " + p.pid());
            System.out.println("Padre del hijo: " +
                    p.toHandle().parent().map(ph -> String.valueOf(ph.pid())).orElse("desconocido"));

        }
        System.out.println("Fin del proceso padre");
    }
}//Ejemplo1
