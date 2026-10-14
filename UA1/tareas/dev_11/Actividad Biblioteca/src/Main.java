public class Main {
    static void main() {
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.añadirLibro("111", "La casa de las hojas", "Mark Z. Danielewski");
        biblioteca.añadirLibro("222", "El imperio Final", "Sanderson");
        biblioteca.añadirLibro("333", "Fenris el elfo", "Laura Gallego");
        biblioteca.añadirLibro("444", "Mort", "Terry Pratchet");

        biblioteca.añadirUsuario(1, "Carlos");
        biblioteca.añadirUsuario(2, "Luis");

        System.out.println("Libros de la biblioteca");
        biblioteca.mostrarLibros();

        System.out.println("Prestamos al usuario 1");
        biblioteca.prestarLibro("111", 1);
        biblioteca.prestarLibro("222", 1);

        System.out.println("Prestamo al usuario 2");
        biblioteca.prestarLibro("333", 2);

        System.out.println("Intento de prestar un libro ya prestado");
        biblioteca.prestarLibro("111", 2);

        System.out.println("Libros de cada usuario");
        for (Usuario usuario : biblioteca.getUsuarios()) {
            System.out.println("Usuario: " + usuario.getNombre());
            if (usuario.getLibros().isEmpty()) {
                System.out.println("  Sin libros");
            }
            for (Libro libro : usuario.getLibros()) {
                System.out.println("  " + libro);
            }
        }

        System.out.println("Devolucion de un libro");
        biblioteca.devolverLibro("111", 1);

        System.out.println("Estado final de la biblioteca");
        biblioteca.mostrarLibros();

        System.out.println("Estado final de los usuarios");
        for (Usuario usuario : biblioteca.getUsuarios()) {
            System.out.println("Usuario: " + usuario.getNombre() + " (" + usuario.getLibros().size() + " libros)");
            for (Libro libro : usuario.getLibros()) {
                System.out.println("  " + libro);
            }
        }
    }
}


