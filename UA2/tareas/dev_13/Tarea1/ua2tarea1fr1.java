public class ua2tarea1fr1 {
    static int contador = 0;

    static void main(String[] args) {

        Thread th1 =new  contadorThreads();
        Thread th2 =new  contadorThreads();
        Thread th3 =new  contadorThreads();
        Thread th4 =new  contadorThreads();
        Thread th5 =new  contadorThreads();

        th1.start(); th2.start(); th3.start();
        th4.start(); th5.start();

    }

    static class contadorThreads extends Thread{
        public void run(){
            contador=contador+1000;
            System.out.println("hilo: " + getId());
            System.out.println("contador: " + contador);
        }
    }
}
//Resultado esperado
//hilo: 36
//hilo: 38
//hilo: 37
//hilo: 39
//hilo: 40
//contador: 5000
//contador: 5000
//contador: 5000
//contador: 5000
//contador: 5000
