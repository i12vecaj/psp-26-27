

 // Guarda el saldo de la cuenta. Los métodos getter y setter duermen al hilo
 // entre 250 y 2000 milisegundos para simular que tardan como dice el ejercicio.

 // El método ingresarSaldo() es synchronized: el hilo que entra toma el
  //monitor (la llave) como el ejemplo con el boli con Sergio de la cuenta y los demás hilos esperan en estado bloqueados
//  hasta que termina. Así solo un hilo a la vez hace el ingreso.

public class CuentaCorriente {

    private int saldo;

    public CuentaCorriente(int saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
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

    // Duerme al hilo un tiempo aleatorio entre 250 y 2000 ms
    private void dormir() {
        int tiempo = (int) (Math.random() * 1751) + 250;
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            // No se deja el catch vacío: se vuelve a marcar la interrupción
            Thread.currentThread().interrupt();
            System.out.println("Se ha interrumpido el hilo " + Thread.currentThread().getName());
        }
    }

    // Añade la cantidad al saldo e indica quién hace el ingreso.
    // FR4: para comprobar el apartado 4 hay que quitar "synchronized" de aquí.
    public synchronized void ingresarSaldo(int cantidad, String nombre) {
        if (cantidad <= 0) {
            System.out.println(nombre + ": la cantidad debe ser mayor que 0, no se ingresa nada");
            return;
        }
        int saldoPrevio = getSaldo();
        int saldoFinal = saldoPrevio + cantidad;
        setSaldo(saldoFinal);
        System.out.println(nombre + " ingresa " + cantidad + " euros. Saldo previo: "
                + saldoPrevio + " -> Saldo final: " + saldoFinal);
    }
}
