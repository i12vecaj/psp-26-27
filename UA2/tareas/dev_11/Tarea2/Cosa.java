package Tarea2;

public class Cosa extends Thread {
    private String nombre;
    private double dinero;

    public Cosa(String nombre, double dinero) {
        this.dinero = dinero;
        this.nombre = nombre;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        super.run();

        if(!CuentaCorriente.añadir(dinero, nombre)){
            System.err.println("No se pudo");
        }
    }
}
