public class ua2tarea1fr1 {

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
