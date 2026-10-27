import javax.imageio.IIOException;
import java.io.IOException;

public class Ejecutador {
    static void main(String[] argumentos) {
        String prueba = "hola";

        try{
            ProcessBuilder pb = new ProcessBuilder("Java", "ValidarArgumentos", prueba);
            Process proceso = pb.start();
            int codigoSalida = proceso.waitFor();

            System.out.println("codigo devuelto" + codigoSalida);

            switch (codigoSalida){
                case 0:
                    System.out.println("correcto");
                    break;
                case 1:
                    System.out.println("faltan argumentos");
                    break;
                case 2:
                    System.out.println("es una cadena");
                    break;
                case 3:
                    System.out.println("menor que cero");
                    break;
                default:
                    System.out.println("error desconocido");
                    break;
            }

        } catch (IOException | InterruptedException e ) {
            System.out.println("error de ejecucion");
            e.printStackTrace();

        }

    }
}
