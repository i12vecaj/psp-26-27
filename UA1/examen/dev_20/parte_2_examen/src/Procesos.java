import java.io.*;
import java.util.concurrent.TimeUnit;

public class Procesos {
    public static void main(String[] args) {
        String[] argsHijos = {"A", "2","B","1","C","8","falla","1","D","-1"};
        Process[] procesos = new Process[5];

        // AQUI LANZO LOS 5 PROCESOS A LA VEZ
        for (int i = 0; i < argsHijos.length; i += 2) {
            try {
                procesos[i/2] = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea", argsHijos[i],argsHijos[i+1]).start();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        // MIRO CADA HIJO EN ORDEN
        for (int i = 0; i < procesos.length; i++) {

            // OBTENGO CODIGO DE SALIDA
            int codigo = 0;
            boolean flag;
            try {
                flag = procesos[i].waitFor(3, TimeUnit.SECONDS);
                codigo = procesos[i].waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            // MUESTRO RESULTADOS
            switch (codigo) {
                case 0: {
                    if (flag == false) {
                        System.err.println("ERROR DE TIEMPO");
                    }
                    System.out.println("Pablo mensaje recibido al padre: "+argsHijos[i * 2]+ " | Codigo: "+codigo);
                    System.out.println("El tiempo de salida es de N s "+argsHijos[i * 2 + 1]);
                    System.out.println("||| TERMINADO |||");
                    System.out.println();
                    break;
                }
                case 1: {
                    System.err.println("FALTAN ARGUMENTOS" + " | Codigo: "+codigo);

                    break;
                }
                case 2: {
                    System.err.println("LOS SEGUNDOS NO SON UN NUMERO"+ " | Codigo: "+codigo);

                    break;
                }
                case 3: {
                    System.err.println("ES NEGATIVO"+ " | Codigo: "+codigo);

                    break;
                }
                case 4: {
                    System.err.println("PALABRA FALLA"+" | Codigo: "+codigo);

                    break;
                }
            }

        }
    }
}