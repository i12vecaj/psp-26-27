import java.io.*;

public class Padre {
    public static void main(String[] args) {
        String[] argsHijos = {"4", "6", "-2", "abc"};
        Process[] procesos = new Process[4];

        // 1. FR2: Lanzar TODOS los procesos A LA VEZ
        for (int i = 0; i < argsHijos.length; i++) {
            try {
                procesos[i] = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Hijo", argsHijos[i]).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int sumaTotal = 0;
        String[] mensajesError = {"OK", "falta argumento", "no es un numero", "numero negativo"};

        // 2. FR3 y FR4: Procesar cada hijo en orden
        for (int i = 0; i < procesos.length; i++) {
            int sumaHijo = 0;

            // Leer lo que imprime el hijo
            try (BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    sumaHijo += Integer.parseInt(linea);
                }
            } catch (Exception e) {
                // Manejo de lectura
            }

            // Obtener código de salida
            int codigo = -1;
            try {
                codigo = procesos[i].waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sumaTotal += sumaHijo;

            // Mostrar el resultado
            String msg = (codigo >= 0 && codigo <= 3) ? mensajesError[codigo] : "error desconocido";
            if (codigo == 0) {
                System.out.println("Hijo " + (i + 1) + " (arg=" + argsHijos[i] + "): codigo " + codigo + " -> " + msg + ", suma=" + sumaHijo);
            } else {
                System.out.println("Hijo " + (i + 1) + " (arg=" + argsHijos[i] + "): codigo " + codigo + " -> " + msg);
            }
        }
        System.out.println("Suma total: " + sumaTotal);
    }
}