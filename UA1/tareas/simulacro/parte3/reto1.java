import java.util.Scanner;

public class Reto1 {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-version");
            Process p = pb.start();

            Scanner sc = new Scanner(p.getErrorStream());

            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }

            int codigo = p.waitFor();
            System.out.println("Codigo: " + codigo);

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}