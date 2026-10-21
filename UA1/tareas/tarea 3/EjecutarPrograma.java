public static void main(String[] args) {

        try {
            // Montamos el comando: java -cp <classpath> ProgramaArgumentos <args...>
            String[] comando = new String[3 + 1 + args.length];
            comando[0] = "java";
            comando[1] = "-cp";
            comando[2] = System.getProperty("java.class.path"); // mismo classpath que este programa
            comando[3] = "ProgramaArgumentos";
            for (int i = 0; i < args.length; i++) {
                comando[4 + i] = args[i];
            }

            ProcessBuilder pb = new ProcessBuilder(comando);
            Process proceso = pb.start();

            // Esperamos a que termine y guardamos el valor devuelto
            int codigo = proceso.waitFor();
            System.out.println("El programa ha devuelto: " + codigo);

            // Mostramos que ha pasado segun el codigo
            switch (codigo) {
                case 0:
                    System.out.println("Todo correcto: el argumento es un entero mayor o igual que 0");
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
                    System.out.println("Codigo desconocido");
            }

        } catch (IOException e) {
            // Falla al arrancar el proceso
            System.out.println("Error al ejecutar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            // Falla mientras esperamos a que termine
            System.out.println("Se ha interrumpido la espera: " + e.getMessage());
        }
    }
