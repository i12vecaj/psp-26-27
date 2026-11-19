public class ua2tarea1fr1 {


    //Principal problema que todos los hilos se ejecutan al mismo tiempo
    // eso no es el metodo que queremos que ocurra explicado en clase el que esta pasando actualmente
    // (secuencial) y nosotros lo queremos poner de manera concurrente

    // por eso en los resultados que he puesto abajo al final del codigo salen todos 5000 debido a que
    // se ejecutan todos a la vez de manera(secuencial)
    // entonces tenemos que dormirlos un par de segundos y
    // usar lo visto hoy en clase como el synchronized que esta en el apartado de ua2tarea1fr2runnable
    // o los join para juntarlos o conectarlos

    //Aqui ponemos el contador que empieze en 0
    static  int contador = 0;


    static void main(String[]args) {


        //Los 5 hilos que nos pide el ejercicio
        Thread thread1 = new ContadordeThreads();
        Thread thread2 = new ContadordeThreads();
        Thread thread3 = new ContadordeThreads();
        Thread thread4 = new ContadordeThreads();
        Thread thread5 = new ContadordeThreads();



        //Aqui se inician todos los hilos
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();







    }

    //Contador de Threads
    static class ContadordeThreads extends Thread{
        public void run() {
            contador = contador + 1000;
            System.out.println("Hilo "+getId());

            System.out.println("contador: " +contador);
        }



    }
    /*Resultado
    Hilo 30
    Hilo 28
    Hilo 31
    Hilo 27
    Hilo 29
    contador: 5000
    contador: 5000
    contador: 5000
    contador: 5000
    contador: 5000*/








}
