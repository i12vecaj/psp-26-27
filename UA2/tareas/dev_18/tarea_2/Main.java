
public class Main {

    private static final double SALDO_INICIAL = 1000.0;
    private static final String[] NOMBRES = {"Ana", "Luis", "Marta", "Pedro"};
    private static final double[] CANTIDADES = {100.0, 200.0, 300.0, 400.0};

    public static void main(String[] args) {
        System.out.println("===== APARTADO 3: CON synchronized =====");
        ejecutar(true);
        System.out.println();
        System.out.println("===== APARTADO 4: SIN synchronized =====");
        ejecutar(false);
    }

    private static void ejecutar(boolean sincronizado) {
        CuentaCorriente cuenta;
        try {
            cuenta = new CuentaCorriente(SALDO_INICIAL);
        } catch (IllegalArgumentException e) {
            System.err.println("No se pudo crear la cuenta: " + e.getMessage());
            return;
        }

        double esperado = SALDO_INICIAL;
        for (double c : CANTIDADES) {
            esperado += c;
        }

        System.out.printf("Saldo inicial: %.2f%n", cuenta.getSaldo());

        HiloIngreso[] hilos = new HiloIngreso[NOMBRES.length];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloIngreso(NOMBRES[i], cuenta, CANTIDADES[i], sincronizado);
        }
        for (HiloIngreso h : hilos) {
            h.start();
        }
        for (HiloIngreso h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Main interrumpido esperando a " + h.getName());
                return;
            }
        }

        double finalReal = cuenta.getSaldo();
        System.out.printf("Saldo final: %.2f (esperado: %.2f) -> %s%n", finalReal, esperado,
                finalReal == esperado ? "CORRECTO" : "INCORRECTO: se han perdido ingresos");
    }
}