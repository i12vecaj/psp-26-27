import java.io.*;

public class Reto2 {
    public static void main() throws Exception {
        if (Reto2.class.getResource("/Eco.class") == null) {
            System.out.println("Eco.class no existe, compilando...");

            File fuente = new File("src/Eco.java");
            if (!fuente.exists()) {
                fuente = new File("Eco.java");
            }
            if (!fuente.exists()) {
                System.out.println("No encuentro Eco.java");
                return;
            }

            Process compilar = new ProcessBuilder(
                    "javac", "-d", ".", fuente.getPath())
                    .inheritIO()
                    .start();
            if (compilar.waitFor() != 0) {
                System.out.println("Error al compilar Eco.java");
                return;
            }
        }
        String cp = System.getProperty("java.class.path")
                + File.pathSeparator + ".";
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, "Eco");
        pb.redirectErrorStream(true);
        Process hijo = pb.start();

        BufferedWriter haciaHijo = new BufferedWriter(
                new OutputStreamWriter(hijo.getOutputStream()));
        BufferedReader desdeHijo = new BufferedReader(
                new InputStreamReader(hijo.getInputStream()));

        for (String s : new String[]{"hola", "mundo", "fin"}) {
            haciaHijo.write(s);
            haciaHijo.newLine();
            haciaHijo.flush();
        }

        String linea;
        while ((linea = desdeHijo.readLine()) != null) {
            System.out.println(linea);
        }

        hijo.waitFor();
    }
}