import java.util.ArrayList;

// Pista 1: Clase que implementa Runnable y recibe los segundos
class TareaEspera implements Runnable {
    private final int segundos;
    private final String nombre;

    public TareaEspera(String nombre, int segundos) {
        this.nombre = nombre;
        this.segundos = segundos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(segundos * 1000L);
            System.out.println("Hilo " + nombre + " terminado tras " + segundos + " s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class RetoCuatro {
    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        // Creamos los 3 hilos con tiempos de 1, 2 y 3 segundos
        Thread h1 = new Thread(new TareaEspera("H1 (1s)", 1));
        Thread h2 = new Thread(new TareaEspera("H2 (2s)", 2));
        Thread h3 = new Thread(new TareaEspera("H3 (3s)", 3));

        // Pista 2: Guardar los hilos en una lista/array para iterar sobre ellos
        Thread[] hilos = { h1, h2, h3 };

        // 1. Lanzar todos los hilos
        for (Thread h : hilos) {
            h.start();
        }

        // 2. Pista 2: Esperar a que TODOS terminen en un segundo bucle
        for (Thread h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long fin = System.currentTimeMillis();
        long tiempoTotal = (fin - inicio) / 1000;
        System.out.println("Tiempo total en main: " + tiempoTotal + " segundos");
    }
}