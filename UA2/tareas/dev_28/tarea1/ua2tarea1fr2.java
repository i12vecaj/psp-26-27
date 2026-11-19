
public class ua2tarea1fr2 {

    //en este parte de la tarea ya estan sincronizados por ua2tarea1fr2runnable()
    // de esta manera esperar a que los demas terminen
    // por poner un ejemplo hoy hemos visto en clase el ejemplo que ha puesto el profesor del boli cuando se lo ha pasado a Sergio
    // y hasta que el no termine no se lo puede pasar a otra persona que seria otro hilo
    // Y por eso va incrementando de 1000 en 1000 hasta llegar a 5000 porque solo tenemos 5 hilos

    static int contador = 0;


    public static void main(String[]args){
        Thread thread1 = new Thread(new  ua2tarea1fr2runnable());
        Thread thread2 = new Thread(new  ua2tarea1fr2runnable());
        Thread thread3 = new Thread(new  ua2tarea1fr2runnable());
        Thread thread4 = new Thread(new  ua2tarea1fr2runnable());
        Thread thread5 = new Thread(new  ua2tarea1fr2runnable());

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

    }

    //Hilo: 27
    //Contador: 1000
    //Hilo: 30
    //Contador: 2000
    //Hilo: 29
    //Contador: 3000
    //Hilo: 28
    //Contador: 4000
    //Hilo: 31
    //Contador: 5000

}
