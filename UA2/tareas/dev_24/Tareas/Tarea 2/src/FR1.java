import java.util.concurrent.ThreadLocalRandom;

public class FR1 {
    //cuenta corriente

    static final int EsperaMin=250;
    static final int EsperaMax=2000;
    private double saldo;



    public FR1 (double saldoInicial){
        if (saldoInicial <0){
            System.out.println("Saldo inicial no puede ser negativo: "+saldoInicial);
        }
        this.saldo=saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public synchronized void ingresar(double cantidad,String quien){
        realizarIngreso(cantidad,quien);
    }
    void realizarIngreso(double cantidad, String quien){
        validarIngreso(cantidad,quien);

        double saldoPrevio= getSaldo();
        double saldofinal=saldoPrevio+cantidad;
        setSaldo(saldofinal);
        System.out.printf("%-10s | ingresa %8.2f € | saldo previo: %9.2f € | saldo final: %9.2f €%n",
                quien, cantidad, saldoPrevio, saldofinal);
    }

    void validarIngreso(double cantidad,String quien){
        if (quien == null || quien.isBlank()){
            System.out.println("debe indicar quien hace el ingreso");
        }

        if (cantidad<=0){
            System.out.println("cantidad debe ser un numero positivo: "+cantidad);
        }
    }

    protected static void dormirAleatorio() {
        int ms = ThreadLocalRandom.current().nextInt(EsperaMin, EsperaMax + 1);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Aviso: " + Thread.currentThread().getName()
                    + " fue interrumpido durante la espera.");
        }
    }
}
