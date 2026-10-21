import java.io.IOException;

    public static void main(String[] args) {

        try {
            // Preparamos el comando: java Validador <argumento>
            ProcessBuilder pb;
            if (args.length > 0) {
                pb = new ProcessBuilder("java", "Validador", args[0]);
            } else {
                pb = new ProcessBuilder("java", "Validador");
            }

            // Lanzamos el proceso y esperamos a que termine
            Process proceso = pb.start();
            int codigo = proceso.waitFor();

            System.out.println("Valor devuelto: " + codigo);

            // Mostramos lo que ha sucedido segun el valor
            switch (codigo) {
                case 0:
                    System.out.println("El argumento es un entero mayor o igual que 0");
                    break;
                case 1:
                    System.out.println("No se ha pasado ningun argumento");
                    break;
                case 2:
                    System.out.println("El argumento es una cadena");
                    break;
                case 3:
                    System.out.println("El argumento es un entero menor que 0");
                    break;
                default:
                    System.out.println("Valor desconocido");
            }

        } catch (IOException e) {
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }