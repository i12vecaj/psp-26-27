public class HiloIngreso extends Thread {

    private final CuentaCorriente cuenta;
    private final double cantidad;
    private final boolean sincronizacion;

    public HiloIngreso(CuentaCorriente cuenta, double cantidad, boolean sincronizacion) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.sincronizacion = sincronizacion;
    }

    @Override
    public void run(){
        if (cuenta == null){
            System.out.println("Error" + getName());
            return;
        }
        if (sincronizacion) {
            cuenta.Ingresar(cantidad, getName());
        }else {
            cuenta.IngresarDineroSinSincronizar(cantidad, getName());
        }

    }
}
