public class Tarea3 {
        String argumento;

    public Tarea3(String argumento) {
        this.argumento = argumento;
    }

    public int run() {
        // si no hay argumento
        if (argumento == null || argumento.isEmpty()) {
            return 1;
        }

        //argumento a cadena y devolver 2
        try {
            int num = Integer.parseInt(argumento);

            //argumento es un número entero menor que 0 debe devolver 3
            if (num < 0) {
                return 3;
            }

            //en el caso correcto
            return 0;

        }catch (NumberFormatException e) {
            // Si no es número = cadena
            return 2;
        }
    }

}
