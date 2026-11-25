// FR1: Crea una clase CuentaCorriente, con un atributo que indique el saldo de la cuenta,
// el constructor que le da un valor inicial al saldo y después los métodos setter y getter.
// En estos métodos deberás añadir un sleep() aleatorio,
// que duerma al hilo durante un número de milisegundos que oscile entre 250 y 2000
// (0,25s y 2s respectivamente).
// Añade también otro método que reciba una cantidad y la añada al saldo,
//  indicando por pantalla el estado previo del saldo, el estado final y quién realiza
//  el ingreso. Deberás definir los parámetros que reciba este método y deberás definirlo
//  como synchronized
public class CuentaCorriente {
    double saldo;
    String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public CuentaCorriente(double saldo, String user) {
        this.saldo = saldo;
        this.user = user;
    }

    public CuentaCorriente(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldoInicial) {
        this.saldo = saldoInicial;
    }
    public void sleep(){
        long espera = 250 + (long) (Math.random() * (2000 - 250 + 1));
        try {
            Thread.sleep(espera);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public synchronized void añadirSaldo (double cantidad, String user){

        double saldoPrevio = this.saldo;

        double saldoFinal = saldoPrevio+cantidad;
        this.saldo = saldoFinal;
        System.out.println("Ingreso realizado por "+user+" Saldo previo :"+saldoPrevio+" Saldo actual: "+saldoFinal);


    }
}
