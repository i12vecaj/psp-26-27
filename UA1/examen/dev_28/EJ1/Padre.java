import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Padre {


    public static void main(String[] args) {
        System.exit(ejecutar());
    }


    //Lanzamiento de los 4  procesos Hijos a la vez
    private static int ejecutar(){
        String[] argumentos = {"4","6", "-2", "abc"};
        Process[] processes = new Process[argumentos.length];




        try{
            // Lanzamiento de cada Hijo
            for (int i = 0; i < argumentos.length; i++){
                ProcessBuilder constructor = new ProcessBuilder (System.getProperty("java.class.path"),"Hijo",
                        argumentos[i],"-cp");
            }


            // Aqui hacemos la sumaTotal de todos los hijos
            long sumaTotal = 0;

            for (int i = 0; i < processes.length; i++) {
                long suma = leerSuma(processes[i]);
                int codigo = processes[i].waitFor();

                sumaTotal += suma;

                System.out.printf("Hijo : (args=):  codigo -> %s, suma=%d%n",
                        i + 1,
                        argumentos[i],
                        codigo,
                        explicarCodigo(codigo),
                        suma);
            }
            System.out.println("Suma total:"+ sumaTotal);
            return 0;
        }catch (IOException | InterruptedException e){
            System.err.println("Error al lanzar o leer un hijo"+ e.getMessage());
            return 1;
        }
    }

        }





// en este private se lee los numeros que escriben un hijo y se calcula su suma cada uno individualmente
private static long leerSuma(Process proceso) throws IOException {
    long suma = 0;

    try (BufferedReader lector = new BufferedReader(
            new InputStreamReader(
                    proceso.getInputStream(),
                    StandardCharsets.UTF_8))) {

        String linea;

        while ((linea = lector.readLine()) != null) {
            try {
                suma += Long.parseLong(linea);
            } catch (NumberFormatException e) {
                throw new IOException(
                        "Salida del hijo no válida: " + linea, e);
            }
        }
    }

    return suma;
}



// Menu en el cual respondemos  (0 correcto, 1 falta argumento, 2 no es un número, 3 número negativo).
    private static String explicarCodigo (int codigo){
        switch (codigo){
            case 0:
                return "correcto";
            case 1:
                return "falta argumento";
            case 2:
                return "no es numero";
            case 3:
                return "numero negativo";
                // Control de errores
            default:
                return "control de errores";
        }
    }
    // He puesto el main para que no me de error
void main() {
}




