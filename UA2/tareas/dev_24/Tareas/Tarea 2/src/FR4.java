public class FR4 extends FR1 {

    public FR4(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public void ingresar(double cantidad, String quien){
        realizarIngreso(cantidad,quien);
    }
}
