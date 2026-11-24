public class ua2tarea1fr2runnable implements Runnable{

    @Override
    public void run() {
        synchronized (ua2tarea1fr2.class){
            ua2tarea1fr2.contador = ua2tarea1fr2.contador + 1000;

            System.out.println("hilo" + Thread.currentThread().getId());
            System.out.println("contador" + ua2tarea1fr2.contador);
        }
    }


}
