 class ua2tarea1fr2runnable implements Runnable {
    public void run(){
        //Utilizamos synchronized que el profesor lo ha explicado en clase y le pasamos nuestra clase
        synchronized (ua2tarea1fr2.class){


            //Utilizamos la clase de ua2tarea1fr2 y vamos asi incrementando el contador
            ua2tarea1fr2.contador = ua2tarea1fr2.contador + 1000;


            //
            System.out.println("Hilo: " + Thread.currentThread().getId());
            System.out.println("Contador: "+ ua2tarea1fr2.contador);
        }



    }



}
