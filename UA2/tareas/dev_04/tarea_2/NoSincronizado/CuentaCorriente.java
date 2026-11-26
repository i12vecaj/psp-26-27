// FR4: la misma cuenta pero sin synchronized en ingresar
public class CuentaCorriente {
    private int saldo;

    public CuentaCorriente(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldo() {
        dormir();
        return saldo;
    }

    public void setSaldo(int saldo) {
        dormir();
        this.saldo = saldo;
    }

    // Sin synchronized: pueden entrar varios hilos a la vez
    public void ingresar(int cantidad, String quien) {
        if (cantidad <= 0) {
            System.out.println("Error: " + quien + " intenta ingresar una cantidad no valida");
            return;
        }
        int saldoPrevio = getSaldo();
        setSaldo(saldoPrevio + cantidad);
        System.out.println(quien + " ingresa " + cantidad + " -> saldo previo: " + saldoPrevio
                + " | saldo final: " + getSaldo());
    }

    // Duerme entre 250 y 2000 ms
    private void dormir() {
        try {
            Thread.sleep((int) (Math.random() * 1751) + 250);
        } catch (InterruptedException e) {
            System.out.println("Error: hilo interrumpido mientras dormia");
        }
    }
}
