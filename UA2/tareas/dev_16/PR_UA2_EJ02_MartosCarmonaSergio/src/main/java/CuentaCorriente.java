public class CuentaCorriente {
private double saldo;

    public CuentaCorriente(double saldoInicial) {
        if(saldoInicial<0){
            System.out.println("Error el saldo inicial no puede ser negativo");
            saldoInicial=0;
        }
        this.saldo = saldo;
    }


    public double getSaldo(){
        esperar();
        return saldo;
    }

    public void setSaldo(double saldoDespues){
        esperar();
        this.saldo=saldo;
    }

    public void esperar(){
        int milisegundos=(int) (Math.random() * (2000-25+1)+250);
        try{
            Thread.sleep(milisegundos);
        }catch (InterruptedException e){
            System.out.println("Error: el hilo ha sido interrumpido mientras esperaba");
        }
    }


    public synchronized void ingresar(String nombre, double cantidad){
        if(cantidad<=0){
            System.out.println("Error: "+nombre+"intentó ingresar una cantidad no valida: "+cantidad);
            return;
        }
        double saldoAntes=getSaldo();
        double saldoDespues= saldoAntes+cantidad;
        setSaldo(saldoDespues);
        System.out.println(nombre +" ingresar "+cantidad+" euros. "+ "Saldo Antes: "+saldoAntes + "Saldo Despues: "+saldoDespues);


    }
}
