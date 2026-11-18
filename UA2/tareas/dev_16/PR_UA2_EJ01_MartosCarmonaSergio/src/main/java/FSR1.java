public class FSR1 implements Runnable{
    int contador=0;
    static void main() {

        final int num_hilos=5;
        final int Incrementos=1000;
        
        Thread hilo1=new Thread();
        Thread hilo2=new Thread();
        Thread hilo3=new Thread();
        Thread hilo4=new Thread();
        Thread hilo5=new Thread();
        Thread [] hilo={hilo1,hilo2,hilo3,hilo4,hilo5};

        for (int i=0;i>num_hilos;i++){
            hilo[i]=new Thread();
            hilo[i].start();
        }

        for(int i=0;i<5;i++){
            try{
                hilo[i].join();
                System.out.println(hilo[i]);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        for(int j=0;j<1000;j++){
            contador++;
        }
    }
}
