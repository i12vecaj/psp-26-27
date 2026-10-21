public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        Libro libro1 = new Libro("111", "El Quijote", "Miguel de Cervantes");
        Libro libro2 = new Libro("222", "Cien años de soledad", "Gabriel García Márquez");
        Libro libro3 = new Libro("333", "1984", "George Orwell");
        Libro libro4 = new Libro("444", "La sombra del viento", "Carlos Ruiz Zafón");

        Usuario usuario1 = new Usuario(1, "Ana");
        Usuario usuario2 = new Usuario(2, "Luis");

        biblioteca.anadirLibro(libro1);
        biblioteca.anadirLibro(libro2);
        biblioteca.anadirLibro(libro3);
        biblioteca.anadirLibro(libro4);
        biblioteca.anadirUsuario(usuario1);
        biblioteca.anadirUsuario(usuario2);

        System.out.println("Libros de la biblioteca");
        biblioteca.mostrarLibros();

        System.out.println("\nPréstamos");
        biblioteca.prestarLibro("111", 1);
        biblioteca.prestarLibro("222", 1);
        biblioteca.prestarLibro("333", 2);
        biblioteca.prestarLibro("111", 2);

        System.out.println("\nLibros de cada usuario");
        usuario1.mostrarLibros();
        usuario2.mostrarLibros();

        System.out.println("\nDevolución");
        biblioteca.devolverLibro("111", 1);

        System.out.println("\nEstado final");
        biblioteca.mostrarLibros();
        usuario1.mostrarLibros();
        usuario2.mostrarLibros();
    }
}
