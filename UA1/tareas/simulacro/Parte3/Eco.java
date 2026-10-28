import java.util.Scanner;

public class Eco {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linea = sc.nextLine();

            if (linea.equalsIgnoreCase("fin")) {
                break;
            }

            System.out.println(linea.toUpperCase());
        }

        sc.close();
    }
}