package com.ceslopedevega.procesos;

import org.ietf.jgss.Oid;

import java.io.IOException;

public class Ejemplo1 {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
        long pid = ProcessHandle.current().pid();
        System.out.println("El PID del proceso padre es: " + pid);

        Process p = pb.start();
        System.out.println("EL PID DEL PROCESO HIJO ES : " + p.pid());

    }
}//Ejemplo1

