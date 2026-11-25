package tarea_2;

public class Main {

    public static void main(String[] args) {

        // FR3: una cuenta compartida con saldo inicial
        tarea_2.CuentaCorriente cuenta = new tarea_2.CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo());
        System.out.println("-----------------------------------");

        // Varios hilos que comparten el MISMO objeto cuenta
        tarea_2.HiloIngreso[] hilos = {
                new tarea_2.HiloIngreso("Ana",    cuenta, 100),
                new tarea_2.HiloIngreso("Luis",   cuenta, 200),
                new tarea_2.HiloIngreso("Marta",  cuenta, 300),
                new tarea_2.HiloIngreso("Pedro",  cuenta, 400)
        };

        // Lanzar los hilos
        for (tarea_2.HiloIngreso h : hilos) {
            h.start();
        }

        // Esperar a que terminen todos antes de mostrar el saldo final
        for (tarea_2.HiloIngreso h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error esperando al hilo: " + e.getMessage());
            }
        }

        System.out.println("-----------------------------------");
        System.out.println("Saldo final: " + cuenta.getSaldo());
        // Con synchronized: 1000 + 100 + 200 + 300 + 400 = 2000
    }
}