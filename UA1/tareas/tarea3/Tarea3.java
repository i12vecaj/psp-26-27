public class Tarea3 {
    private String argumento;

    public Tarea3(String argumento) {
        this.argumento = argumento;
    }

    public int run(){
            if (argumento == null ||argumento.isEmpty()){
                return 1;

            }

            try{
                int numero = Integer.parseInt(argumento);

                if (numero < 0){
                    return 3;
                }

                return 0;

            }catch (NumberFormatException e){

                return 2;
            }
    }
}