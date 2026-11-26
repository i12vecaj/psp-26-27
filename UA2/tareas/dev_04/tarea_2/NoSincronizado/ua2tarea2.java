// FR4: sin synchronized los hilos leen el saldo a la vez, se pisan y se pierden ingresos
public class ua2tarea2 {
    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        HiloIngreso[] hilos = {
                new HiloIngreso("Neus", cuenta, 100),
                new HiloIngreso("Pepe", cuenta, 250),
                new HiloIngreso("Lucia", cuenta, 300)
        };

        for (HiloIngreso hilo : hilos) {
            hilo.start();
        }

        try {
            for (HiloIngreso hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error: main interrumpido esperando a los hilos");
        }

        System.out.println("Saldo final: " + cuenta.getSaldo());
    }
}
