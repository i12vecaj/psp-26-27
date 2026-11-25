//FR2 FR2: Crea una clase que extienda de Thread, y desde el método run() usará el método de la
// clase CuentaCorriente que permite añadir saldo a la cuenta

//Creamos la clase Ingreso, la cual pide una cuenta, una cantidad, y un usuario para hacer un ingreso a dicha cuenta
// y con el método run ejecuta el método de añadir saldo de la clase de CuentaCorriente
public class Ingreso extends Thread{
    private final CuentaCorriente cuenta;
    private final double cantidad;
    private final String user;

public Ingreso (CuentaCorriente cuenta, double cantidad, String user){
    super (user);
    this.cuenta=cuenta;
    this.cantidad=cantidad;
    this.user=user;
    }
    @Override
    public void run () {
    cuenta.añadirSaldo(cantidad,user);
    }
}
