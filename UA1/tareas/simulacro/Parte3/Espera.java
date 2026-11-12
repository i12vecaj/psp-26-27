public class Espera {
    public static void main(String[] args) throws Exception {
        int segundos = Integer.parseInt(args[0]);
        Thread.sleep(segundos * 1000L);
    }
}