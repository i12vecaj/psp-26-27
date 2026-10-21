public class Argumentos {
    static void main(String[] argumentos) {

        //En este lo que estamos haciendo es restringir que si el numero de argumentos es < 1 el Sistema devuelve 1
        if (argumentos.length < 1){
           System.exit(1);
       }

        // En este estamos creando la cadena argumento y la estamos igualando argumentos a 0
       String argumento = argumentos[0];

       try{
           // En este estamos creamos la variable int numero y volvemos a entero argumento
           int numero = Integer.parseInt(argumento);




           if (numero < 0){
               System.exit(3);//Si el argumento es un número entero menor que 0 debe devolver 3
           } else {
               System.exit(0);//En cualquier otro caso debe devolver 0
           }




       }catch (NumberFormatException e){
           System.exit(2);//Si el argumento es una cadena debe devolver 2
       }





    }


}
