package com.ceslopedevega.procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tarea1_2 {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

public static void main(String[] args) throws IOException, InterruptedException {
String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}:new String[]{"ls", "-l"};;

// 1. TODO: crea el ProcessBuilder con el comando
    Process pb = new ProcessBuilder(comando).start();
    int numLinea = 0;

// 2. TODO: lanza el proceso con start()
    Long pid = pb.pid();

// 3. TODO: muestra el PID
    System.out.println("El pid es: "+pid);

// 4. TODO: lee la salida línea a línea (BufferedReader + InputStreamReader)
// y numera cada línea
    try {
        BufferedReader br = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        String linea;


        while ((linea = br.readLine()) != null) {
            numLinea++;
            System.out.println(numLinea + ": " + linea);
        }
        br.close();


    } catch (Exception e) {
        e.printStackTrace();
    }

// 5. TODO: espera a que termine con waitFor()
    int exitVal;
    try {
        exitVal = pb.waitFor();
        System.out.println("Valor de Salida: " + exitVal);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

// 6. TODO: muestra líneas leídas y código de salida (0 = correcto)
    System.out.println("Numero de lineas: "+ numLinea);

}
}
