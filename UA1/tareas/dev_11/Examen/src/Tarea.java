

public class Tarea extends Thread {
    private final String[] cosa;

    public Tarea(String[] args) {
       this.cosa = args;

    }

    public void run() {
        if (cosa.length < 2) {
            System.err.println("Faltan argumentos");
            System.exit(1);
        }
        try {
            if (Integer.parseInt(cosa[1]) < 0) {
                System.err.println("Los segundos son negativos");
                System.exit(3);
            }

        } catch (NumberFormatException e) {
            System.err.println("El segundo argumento no es un numero");
            System.exit(2);
        }

        System.out.println(cosa[0] + " mensaje recibido: " + cosa[0]);

        if (cosa[0].equalsIgnoreCase("falla")) {
            System.err.println("[Falla] Error simulado");
            System.exit(4);
        }

        int segundos= Integer.parseInt(cosa[1]);

        System.out.println("Trabajando " + segundos + "s.");
        for(int i = 0; i <= segundos; i++){
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println(cosa[0] + " Terminado");
        System.exit(0);


    }
}

