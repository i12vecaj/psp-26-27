

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Planificador {
    public static void main(String[] args) {

        ProcessBuilder pb1 = new ProcessBuilder("java", "org.example.Tarea", "A", "2");
        ProcessBuilder pb2 = new ProcessBuilder("java", "org.example.Tarea", "B", "1");
        ProcessBuilder pb3 = new ProcessBuilder("java", "org.example.Tarea", "C", "8");
        ProcessBuilder pb4 = new ProcessBuilder("java", "org.example.Tarea", "falla", "1");
        ProcessBuilder pb5 = new ProcessBuilder("java", "org.example.Tarea", "D", "-1");
       File ficheroErrores = new File("errores.log");
       pb1.redirectError(ProcessBuilder.Redirect.appendTo(ficheroErrores));
        pb2.redirectError(ProcessBuilder.Redirect.appendTo(ficheroErrores));
        pb3.redirectError(ProcessBuilder.Redirect.appendTo(ficheroErrores));
        pb4.redirectError(ProcessBuilder.Redirect.appendTo(ficheroErrores));
        pb5.redirectError(ProcessBuilder.Redirect.appendTo(ficheroErrores));

        try {
            Process T1 = pb1.start();
            BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(T1.getOutputStream()));
            bw1.write("Hola desde padre A");
            bw1.newLine();
            bw1.close();
            Process T2 = pb2.start();
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(T2.getOutputStream()));
            bw2.write("Hola desde padre B");
            bw2.newLine();
            bw2.close();
            Process T3 = pb3.start();
            BufferedWriter bw3 = new BufferedWriter(new OutputStreamWriter(T3.getOutputStream()));
            bw3.write("Hola desde padre C");
            bw3.newLine();
            bw3.close();
            Process T4 = pb4.start();
            BufferedWriter bw4 = new BufferedWriter(new OutputStreamWriter(T4.getOutputStream()));
            bw4.write("Hola desde padre falla");
            bw4.newLine();
            bw4.close();
            Process T5 = pb5.start();
            BufferedWriter bw5 = new BufferedWriter(new OutputStreamWriter(T5.getOutputStream()));
            bw5.write("Hola desde padre D");
            bw5.newLine();
            bw5.close();
            String linea;
            BufferedReader br1 = new BufferedReader(new InputStreamReader(T1.getInputStream()))
                    while ((linea = br1.readLine())!=null){
                        System.out.println(linea);
                    }
                    br1.close();
                    T1.waitFor();
                    //REPETIR PARA LOS OTROS 4

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

