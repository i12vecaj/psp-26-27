package Tarea3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanzarArgumentos {

    public static void main(String[] args) {
       
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

        
        List<String> comando = new ArrayList<>();
        comando.add(java);
        comando.add("-cp");
        comando.add(classpath);
        comando.add("Tarea3.Argumentos");
        for (String arg : args) {
            comando.add(arg);
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.inheritIO();
            Process proceso = pb.start();

           
            int salida = proceso.waitFor();

            switch (salida) {
                case 0:
                    System.out.println("Valor 0: el argumento es un número entero mayor o igual que 0.");
                    break;
                case 1:
                    System.out.println("Valor 1: no se ha introducido ningún argumento.");
                    break;
                case 2:
                    System.out.println("Valor 2: el argumento es una cadena.");
                    break;
                case 3:
                    System.out.println("Valor 3: el argumento es un número entero menor que 0.");
                    break;
                default:
                    System.out.println("Valor inesperado: " + salida);
            }
        } catch (IOException e) {
            System.err.println("Error del proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso ha sido interrumpio: " + e.getMessage());
        }
    }
}
