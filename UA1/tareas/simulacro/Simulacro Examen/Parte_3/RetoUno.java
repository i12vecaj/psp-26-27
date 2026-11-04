import java.io.*;

public class RetoUno {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process p = new ProcessBuilder("java", "-version").start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
        br.close();
        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Mi predicion era que no iba a ejecutar con getInputStream, debia usar el codigo de getErrorStream para funcionar
// Finalmente eso es lo que ocurre, al ejecutarlo comienza el proceso y da error, el error es la version
// y otros componentes que debe de darte, finalmente en un try validamos el 0