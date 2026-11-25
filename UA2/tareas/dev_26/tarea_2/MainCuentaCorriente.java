import java.util.ArrayList;
import java.util.List;

/**
 * FR3: Crea la cuenta con saldo inicial, muestra el saldo inicial, lanza varios hilos
 * compartiendo la cuenta, espera con join() a que terminen y muestra el saldo final.
 *
 * FR4 (diferencia al quitar 'synchronized' en ingresar()):
 * - Con 'synchronized': el saldo final siempre es igual al saldo inicial más todos los ingresos.
 * - Sin 'synchronized': dos o más hilos pueden leer el mismo saldo previo antes de que el otro
 *   escriba el nuevo saldo, perdiéndose ingresos y saliendo un saldo final menor o distinto cada vez.
 */
public class MainCuentaCorriente {

    public static void main(String[] args) {
        try {
            CuentaCorriente cuenta = new CuentaCorriente(1000.0);
            System.out.println("Saldo inicial: " + cuenta.getSaldo());

            List<HiloIngreso> hilos = new ArrayList<>();
            hilos.add(new HiloIngreso("Hilo-1", cuenta, 200.0));
            hilos.add(new HiloIngreso("Hilo-2", cuenta, 300.0));
            hilos.add(new HiloIngreso("Hilo-3", cuenta, 150.0));
            hilos.add(new HiloIngreso("Hilo-4", cuenta, 250.0));

            for (HiloIngreso hilo : hilos) {
                hilo.start();
            }

            for (HiloIngreso hilo : hilos) {
                hilo.join();
            }

            System.out.println("Saldo final: " + cuenta.getSaldo());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo principal interrumpido: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error de configuración: " + e.getMessage());
        }
    }
}