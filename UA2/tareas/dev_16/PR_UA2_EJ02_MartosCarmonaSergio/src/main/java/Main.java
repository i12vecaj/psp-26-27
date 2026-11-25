public class Main {
    static void main() {
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo() + " euros");


        HiloIngreso h1 = new HiloIngreso("Ana", 100, cuenta);
        HiloIngreso h2 = new HiloIngreso("Luis", 200, cuenta);
        HiloIngreso h3 = new HiloIngreso("Marta", 50, cuenta);
        HiloIngreso h4 = new HiloIngreso("Pedro", 150, cuenta);

        h1.start();
        h2.start();
        h3.start();
        h4.start();

        try{
            h1.join();
            h2.join();
            h3.join();
            h4.join();
        }catch (InterruptedException e){
            System.out.println("Error: El programa fue interrumpido mientras se espeeraba a los hilos");
        }

        System.out.println("Saldo final: "+cuenta.getSaldo()+ " euros");
    }


}
