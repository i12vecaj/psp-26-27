package com.ceslopedevega.procesos;

import java.io.*;

public class Codigodelpadre {

    public static void main(String[] args) {

        File directorio = new File("bin");

        ProcessBuilder pb = new ProcessBuilder("java",
                "com.ceslopedevega.procesos.LeerHastaASterisco");
        pb.directory(directorio);

        try {
            Process p = pb.start();

            OutputStream os = p.getOutputStream();
            os.write("Hola Manuel*esto ya no se lee".getBytes());
            os.close();

            InputStream is = p.getInputStream();
            int c;
            while ((c = is.read()) != -1) {
                System.out.print((char) c);
            }
            is.close();

            BufferedReader brer = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String liner;
            while ((liner = brer.readLine()) != null) {
                System.err.println("ERROR del hijo > " + liner);
            }
            brer.close();

            int exitVal = p.waitFor();
            switch (exitVal) {
                case 0:
                    System.out.println("El hijo terminó correctamente (código 0).");
                    break;
                case 1:
                    System.out.println("El hijo no recibió el carácter de terminación '*' (código 1).");
                    break;
                case 2:
                    System.out.println("El hijo tuvo un error al leer la entrada (código 2).");
                    break;
                default:
                    System.out.println("El hijo terminó con un código inesperado: " + exitVal);
            }

        } catch (IOException e) {
            System.err.println("Error al lanzar o comunicarse con el proceso hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Se interrumpió la espera del proceso hijo: " + e.getMessage());
        }
    }
}