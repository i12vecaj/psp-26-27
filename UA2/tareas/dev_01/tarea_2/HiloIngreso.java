public class HiloIngreso extends Thread {

    private CuentaCorriente cuenta;
    private String persona;
    private double cantidad;
    public HiloIngreso(CuentaCorriente cuenta, String persona, double cantidad) {
        this.cuenta = cuenta;
        this.persona = persona;
        this.cantidad = cantidad;
    }

    // Todos los hilos realizan un ingreso en la cuenta
    @Override
    public void run() {
        cuenta.ingresar(cantidad, persona);
    }
}