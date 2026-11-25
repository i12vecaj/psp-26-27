
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"} :
                new String[]{"ls", "-l"};

// 1. TODO: crea el ProcessBuilder con el comando
        ProcessBuilder pb = new ProcessBuilder(comando);

// 2. TODO: lanza el proceso con start()
        Process p = new ProcessBuilder(comando).start();
        long pid = p.pid();
// 3. TODO: muestra el PID

        System.out.println("El pid es " + pid);

// 4. TODO: lee la salida línea a línea (BufferedReader + InputStreamReader) y numera cada línea
        try {

            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;
            int contadorLineas = 1;
            // Cambiamos el método a un buffered reader para numerar las líneas
            while ((linea = br.readLine()) != null) {
                // Mostramos el número de línea seguido del contenido
                System.out.println(contadorLineas + ": " + linea);
                contadorLineas++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

// 5. TODO: espera a que termine con waitFor()
        int exitVal;
        try {
            exitVal = p.waitFor();

// 6. TODO: muestra líneas leídas y código de salida (0 = correcto)
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }