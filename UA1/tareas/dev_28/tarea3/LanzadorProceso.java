import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LanzadorProceso {

    static void main(String[] args) {

        String[][] casosdePrueba = {
                {},
                {"Hola Mundo"},
                {"-15"},
                {"25"}



        };

// Recorrido secuencial con un bucle for
        for (int i = 0; i < casosdePrueba.length; i++) {
            System.out.println("Prueba"+ (i+1));
            ejecutarYEvaluar(casosdePrueba[i]);
        }

    }

    static void ejecutarYEvaluar(String[] argumentosPrueba) {
        try{

            // Obtencion de la ruta  ejectubale de java
            String javaHome = System.getProperty("java.home");
            String javaBin;

            // evitar duplicados de la carpeta bin
            if (javaHome.toLowerCase().endsWith("bin")) {
                javaBin = javaHome + File.separator + "java.exe";
            } else {
                javaBin = javaHome + File.separator + "bin" + File.separator + "java.exe";
            }

            //Construccion con arraylist para poder ejecutar con el comando
            List<String> comando = new ArrayList<>();
            comando.add(javaBin);
            comando.add("-cp");
            comando.add(System.getProperty("java.class.path"));
            comando.add("Argumentos");

            //Agregando los procesos de la prueba anterior
            for (String arg : argumentosPrueba){
                comando.add(arg);
            }

            //Inica el proceso
            ProcessBuilder builder = new ProcessBuilder(comando);
            Process proceso = builder.start(); //Arranca el subproceso

            int codigoSalida = proceso.waitFor();//Obtiene el valor obtenido

            //Presentacion de los resultados
            String argsTexto = argumentosPrueba.length > 0 ? String.join("", argumentosPrueba) : "(sin argumentos)";
            System.out.println("Argumento enviado"+argsTexto);
            System.out.println("Codigo devuelto por System.exit(): " +codigoSalida);

            // Menu
            switch (codigoSalida){
                case 0:
                    System.out.println("El argumento es un entero mayor o igual a 0");
                    break;
                case 1:
                    System.out.println("Error(1) No se enviaron argumentos");
                    break;
                case 2:
                    System.out.println("Error(2) El argumento es una cadena de texto no numerica");
                    break;
                case 3:
                    System.out.println("Error (3) El argumento es un entero menor que 0");
                    break;
                default:
                    System.out.println("Codiog desconcido: "+ codigoSalida);
                    break;
            }

            //Control de errores con catch
        }catch (IOException e){
            System.err.println("Error de entrada/salida"+ e.getMessage());
        }catch (InterruptedException e){
            System.err.println("El proceso principal fue interrumpido"+e.getMessage());
            Thread.currentThread().interrupt();
        }
    }



}
