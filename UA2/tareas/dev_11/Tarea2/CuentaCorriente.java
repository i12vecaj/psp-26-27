package Tarea2;

public class CuentaCorriente {
    /** Objeto que permite la sincronizacion*/
    private static final Object CERROJO = new Object();
    private static double saldo;

    public CuentaCorriente(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public static void sleep() throws InterruptedException {
        int nanosegundos = Math.toIntExact(Math.round(Math.random() * 1750 + 250));
        Thread.sleep(nanosegundos);
    }
    /** Metodo que añade dinero al saldo si la cantidad es positiva
     * @param dinero double
     * @param nombre String
     * @return Devuelve true si se ha podido y false si no
     *
     * */
    public static boolean añadir(double dinero, String nombre) {
        if (dinero<0){
            System.err.println("No se pueden añadir cantidades negativas");
            return false;
        }
        synchronized (CERROJO) {
            System.out.println("Saldo inicial= " + saldo + "\n Persona que ingresa: " + nombre+"\nIngresa :"+dinero);
            saldo += dinero;
            System.out.println("Saldo final= " + saldo);
            return true;
        }
    }
}
