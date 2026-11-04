package org.example;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class Planificador {
    public static void main(String[] args) {
        String[][] tareas = {
                {"A", "2"}, {"B", "1"}, {"C", "8"}, {"falla", "1"}, {"D", "-1"}
        };

        Process[] ps = new Process[tareas.length];
        File log = new File("errores.log");

        for (int i = 0; i < tareas.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "java", "-cp", System.getProperty("java.class.path"), "Tarea", tareas[i][0], tareas[i][1]
                );
                pb.redirectError(ProcessBuilder.Redirect.appendTo(log));
                ps[i] = pb.start();

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ps[i].getOutputStream()));
                bw.write("Hola desde el padre a " + tareas[i][0]);
                bw.newLine();
                bw.flush();
                bw.close();
            } catch (Exception e) {}
        }

        int ok = 0, error = 0, timeout = 0;
        String[] estados = new String[tareas.length];
        String[][] salidas = new String[tareas.length][10];
        int[] numLineas = new int[tareas.length];

        for (int i = 0; i < tareas.length; i++) {
            try {
                if (ps[i].waitFor(3, TimeUnit.SECONDS)) {
                    int codigo = ps[i].exitValue();
                    if (codigo == 0) {
                        estados[i] = "OK (codigo 0)";
                        ok++;
                    } else {
                        estados[i] = "ERROR (codigo " + codigo + ")";
                        error++;
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(ps[i].getInputStream()));
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        salidas[i][numLineas[i]++] = linea;
                    }
                } else {
                    ps[i].destroyForcibly();
                    estados[i] = "TIMEOUT (cancelada)";
                    timeout++;
                }
            } catch (Exception e) {}
        }

        for (int i = 0; i < tareas.length; i++) {
            System.out.println("Tarea " + tareas[i][0] + ": " + estados[i]);
            if (!estados[i].startsWith("TIMEOUT")) {
                for (int j = 0; j < numLineas[i]; j++) {
                    System.out.println("   " + salidas[i][j]);
                }
            }
        }

        System.out.println("Resumen: " + ok + " OK, " + error + " ERROR, " + timeout + " TIMEOUT");
    }
}