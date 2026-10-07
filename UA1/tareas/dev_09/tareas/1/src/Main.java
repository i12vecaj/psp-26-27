public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        Libro libro1 = new Libro("978-84-376-0494-7", "Don Quijote de la Mancha", "Miguel de Cervantes");
        Libro libro2 = new Libro("978-84-663-5120-2", "La sombra del viento", "Carlos Ruiz Zafón");
        Libro libro3 = new Libro("978-84-204-8270-5", "El camino", "Miguel Delibes");
        Libro libro4 = new Libro("978-84-9759-379-3", "La ciudad y los perros", "Mario Vargas Llosa");
        Usuario usuario1 = new Usuario(1, "Ana");
        Usuario usuario2 = new Usuario(2, "Luis");

        biblioteca.anadirLibro(libro1);
        biblioteca.anadirLibro(libro2);
        biblioteca.anadirLibro(libro3);
        biblioteca.anadirLibro(libro4);
        biblioteca.anadirUsuario(usuario1);
        biblioteca.anadirUsuario(usuario2);

        biblioteca.mostrarTodosLosLibros();
        System.out.println("\n--- Préstamos ---");
        biblioteca.prestarLibro(libro1.getIsbn(), usuario1.getId());
        biblioteca.prestarLibro(libro2.getIsbn(), usuario1.getId());
        biblioteca.prestarLibro(libro3.getIsbn(), usuario2.getId());
        biblioteca.prestarLibro(libro1.getIsbn(), usuario2.getId());

        System.out.println("\n--- Libros por usuario ---");
        usuario1.mostrarLibros();
        usuario2.mostrarLibros();

        System.out.println("\n--- Devolución ---");
        biblioteca.devolverLibro(libro1.getIsbn(), usuario1.getId());

        System.out.println("\n--- Estado final ---");
        biblioteca.mostrarTodosLosLibros();
        usuario1.mostrarLibros();
        usuario2.mostrarLibros();
    }
}
