public class ua2tarea1fr1 {

    static int cont=0;

    public static void main(String[] args) {

        Thread thread1=new ContThread();
        Thread thread2=new ContThread();
        Thread thread3=new ContThread();
        Thread thread4=new ContThread();
        Thread thread5=new ContThread();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

    }

    static class ContThread extends Thread{
        public void run(){
            cont=cont+1000;
            System.out.println("Hilo: "+getId());

            System.out.println("Contador: "+cont);
        }
    }
}
