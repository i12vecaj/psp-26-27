import java.io.*;

/*
## Enunciado

Crea un programa Java que ejecute un comando del sistema y muestre un **informe** de lo que ha pasado. Debe hacer, por este orden:

1. **Preparar** el comando con `ProcessBuilder`.
2. **Lanzarlo** con `start()`.
3. **Mostrar el PID** del proceso hijo con `pid()`.
4. **Leer su salida** con `getInputStream()`, numerando cada línea.
5. **Esperar** a que termine con `waitFor()`.
6. **Mostrar el informe final:** número de líneas leídas y código de salida, indicando si fue correcto (0) o falló (distinto de 0).[18:53]import java.io.*;

*/

public class MiniMonitor {
    // Creas una constante boleana que mire como se llama el sistema eso lo hace con System.getproperty,
    // lo pasa a minuscula y mira si contiene win para saber si estas en windows
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        //Esto es un condicional ? si_verdadero : si_falso
        //en este caso si WIN es true te crea el comando para windows si es false te crea el comando para linux
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"} : new String[]{"ls", "-l"};

        // 1. TODO: crea el ProcessBuilder con el comando
        // 2. TODO: lanza el proceso con start()
        Process p = new ProcessBuilder(comando).start();

        // 3. TODO: muestra el PID
        System.out.println("PID del proceso " + p.pid());
        System.out.println("-------------------------------------------------");

        // 4. TODO: lee la salida línea a línea (BufferedReader + InputStreamReader)
        // y numera cada línea
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );

            String Linea;
            int numeroLinea = 0;

            while ((Linea = reader.readLine()) != null){
                numeroLinea ++;
                System.out.println(numeroLinea + " : " + Linea);
            }

            // 5. TODO: espera a que termine con waitFor()
            int codigo = p.waitFor();
            // 6. TODO: muestra líneas leídas y código de salida (0 = correcto)
            System.out.println("--- Informe ---");
            System.out.println("Líneas leídas: " + numeroLinea);
            System.out.println("Código de salida: " + codigo
                    + (codigo == 0 ? " (correcto)" : " (error)"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}