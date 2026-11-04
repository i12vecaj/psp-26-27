public class Espera {
    public static void main(String[] args) {
        try {
            // Convierte el argumento recibido a milisegundos y duerme el hilo
            int segundos = Integer.parseInt(args[0]);
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}