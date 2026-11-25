import static java.lang.Character.getName;

public class HiloIngreso extends Thread{
    private CuentaCorriente cuenta;
    private double cantidad;


    public HiloIngreso(String nombre, double cantidad, CuentaCorriente cuenta) {
        super();
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run(){
        if(cuenta==null){
            System.out.println("Error "+getName()+ "no tiene cuenta para ingresar");
            return;
        }
        cuenta.ingresar(getName(),cantidad);
    }


}
