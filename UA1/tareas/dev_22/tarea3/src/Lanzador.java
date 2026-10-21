
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    public static void main(String[] args) {

        try {

            String java = System.getProperty("java.home")
                    + File.separator + "bin"
                    + File.separator + "java";


            if (File.separator.equals("\\")) {
                java += ".exe";
            }


            List<String> comando = new ArrayList<>();
            comando.add(java);
            comando.add("-cp");
            comando.add(System.getProperty("java.class.path"));
            comando.add("Principal");


            for (String argumento : args) {
                comando.add(argumento);
            }


            ProcessBuilder pb = new ProcessBuilder(comando);
            Process proceso = pb.start();


            proceso.waitFor();

            int codigo = proceso.exitValue();


            switch (codigo) {
                case 0:
                    System.out.println(
                            "El argumento es un entero válido.");
                    break;

                case 1:
                    System.out.println(
                            "Error: no se ha recibido ningún argumento.");
                    break;

                case 2:
                    System.out.println(
                            "Error: el argumento no es un número entero.");
                    break;

                case 3:
                    System.out.println(
                            "Error: el número es negativo.");
                    break;

                default:
                    System.out.println(
                            "Error: código de salida desconocido.");
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al ejecutar Principal: " + e.getMessage());

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            System.out.println(
                    "Se ha interrumpido la ejecución del proceso.");
        }
    }

