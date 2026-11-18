public class FSR2 implements Runnable{
    static int contador=0;
    static void main() {
Thread hilos[]=new Thread[5];
    for (int i=0;i<5;i++){
        hilos[i]=new Thread();
        hilos[i].start();
    }
    for (int i=0;i<5;i++){
       try{
           hilos[i].join();

       }catch (InterruptedException e){
           System.out.println(e.getMessage());
       }
    }


        System.out.println("Resultado con Runnable: "+contador);
    }

    @Override
    public void run() {

    }
}
