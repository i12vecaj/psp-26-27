/**
 * Proceso hijo: duerme los segundos que recibe como argumento y termina.
 * Uso: java Espera 2
 */
public class Espera {
    public static void main(String[] args) throws InterruptedException {
        int segundos = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        Thread.sleep(segundos * 1000L);
        System.out.println("Espera de " + segundos + " s terminada");
        System.exit(0);
    }
}
