import java.sql.SQLOutput;

public class Ejecutor {
    static void main() {
        //Implementamos el processBuilder
        String classPath = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder("java","-cp",classPath, "Main");
        pb.inheritIO();
        //El codigo para iniciar el programa dentro del try
        try{
            System.out.println("Escribe tu texto y usa '*' para terminar:");

            Process procesoHijo = pb.start();

            // Esperamos a que el proceso hijo finalice
            int codigoSalida = procesoHijo.waitFor();
            System.out.println("Programa finalizado con codigo: " + codigoSalida);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
