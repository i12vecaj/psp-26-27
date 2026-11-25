void main() {
    System.out.println("Iniciando el proceso de ejecución de Programa1");
    try {
        // FR3: Configuración del proceso para ejecutar la clase compilada anterior
        ProcessBuilder processBuilder = new ProcessBuilder("java", "Programa1");
        // Heredamos la consola estándar (I/O) para poder interactuar directamente con el proceso hijo
        processBuilder.inheritIO();
        // Lanzamos el proceso
        Process proceso = processBuilder.start();
        int codigoSalida = proceso.waitFor();
        System.out.println("El Programa 1 ha finalizado");


    } catch (IOException e) {
        // Control de errores si el archivo no se encuentra o hay problemas al iniciar el proceso
        System.err.println("[ERROR] Error de E/S al intentar ejecutar el proceso: " + e.getMessage());
    } catch (Exception e) {
        // Control de errores general para cualquier incidencia imprevista
        System.err.println("[ERROR INESPERADO]: " + e.getMessage());
        e.printStackTrace();
    }
}