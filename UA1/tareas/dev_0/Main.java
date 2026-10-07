public class Main {
    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        // Crear libros
        Libro l1 = new Libro("111", "El Quijote", "Cervantes");
        Libro l2 = new Libro("222", "1984", "George Orwell");
        Libro l3 = new Libro("333", "El Hobbit", "Tolkien");
        Libro l4 = new Libro("444", "Dune", "Frank Herbert");

        // Crear usuarios
        Usuario u1 = new Usuario(1, "David");
        Usuario u2 = new Usuario(2, "Lucía");

        // Añadir a la biblioteca
        biblioteca.añadirLibro(l1);
        biblioteca.añadirLibro(l2);
        biblioteca.añadirLibro(l3);
        biblioteca.añadirLibro(l4);

        biblioteca.añadirUsuario(u1);
        biblioteca.añadirUsuario(u2);

        // Mostrar libros
        biblioteca.mostrarLibros();

        // Prestar libros
        biblioteca.prestarLibro("111", 1);
        biblioteca.prestarLibro("222", 1);
        biblioteca.prestarLibro("333", 2);

        // Intentar prestar un libro ya prestado
        biblioteca.prestarLibro("111", 2);

        // Mostrar libros de cada usuario
        u1.mostrarLibros();
        u2.mostrarLibros();

        // Devolver un libro
        biblioteca.devolverLibro("111", 1);

        // Estado final
        biblioteca.mostrarLibros();
        u1.mostrarLibros();
        u2.mostrarLibros();
    }
}
