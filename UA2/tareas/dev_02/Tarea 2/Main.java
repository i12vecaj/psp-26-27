
public class Main {
    //FR3 Crea en el método main un objeto de tipo CuentaCorriente asignando un valor inicial y visualiza el saldo inicial.
    // Ahora crea varios hilos que compartan dicho objeto. Cada hilo recibirá un nombre y tendrá una cantidad de dinero.
    // Lanza los hilos y espera a que finalicen para visualizar el saldo final de la cuenta

    public static void main(String[] args) {
    CuentaCorriente cuenta = new CuentaCorriente(1000,"Manolo");
        System.out.println("Cuenta con saldo inicial de 1000 euros");
        //Creamos varios hilos de ingresos de distintos usuarios con distintas cantidades, introduciendo cantidad y usuario
        Ingreso i1 = new Ingreso(cuenta,100, "Manu");
        Ingreso i2 = new Ingreso(cuenta,50,"Martin");
        Ingreso i3 = new Ingreso (cuenta, 24,"El Carlos");
        i1.start();
        i2.start();
        i3.start();
//FR4 Simplemente cambia el orden pero el resultado llega a ser el mismo, si las operaciones fuesen distintas el
// resultado podría ser fatal ya que podría intentar quitarse saldo de una cantidad inexistente

//FR5 Implementamos el try catch para controlar errores
        try {
            i1.join();
            i2.join();
            i3.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Actividad interrumpida esperando otros hilos.");
        }









    }
}
