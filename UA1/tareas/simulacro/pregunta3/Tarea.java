public class Tarea implements Runnable {

    private String nombre;
    private int segundos;

    public Tarea(String nombre, int segundos) {
        this.nombre = nombre;
        this.segundos = segundos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(segundos * 1000);
            System.out.println(nombre + " terminado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
