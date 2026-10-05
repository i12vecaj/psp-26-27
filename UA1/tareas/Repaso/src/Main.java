import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        Biblioteca biblioteca = new Biblioteca();

        Libro libro1 = new Libro(false, "J.R.R. Tolkien", "El Señor de los Anillos", "1111");
        Libro libro2 = new Libro(false, "George Orwell", "1984", "2222");
        Libro libro3 = new Libro(false, "Gabriel García Márquez", "Cien años de soledad", "3333");
        Libro libro4 = new Libro(false, "Isaac Asimov", "Fundación", "4444");

        Usuario usuario1 = new Usuario(1, "Ana", new ArrayList<>());
        Usuario usuario2 = new Usuario(2, "Carlos", new ArrayList<>());

        biblioteca.añadirLibro(libro1);
        biblioteca.añadirLibro(libro2);
        biblioteca.añadirLibro(libro3);
        biblioteca.añadirLibro(libro4);

        biblioteca.añadirUsuario(usuario1);
        biblioteca.añadirUsuario(usuario2);

        biblioteca.mostrarLibros();

        biblioteca.prestarLibro("1111", 1);
        biblioteca.prestarLibro("2222", 1);

        biblioteca.prestarLibro("3333", 2);

        biblioteca.prestarLibro("1111", 2);

        System.out.println("Libros de " + usuario1.getNombre() + ":");
        usuario1.mostrarLibros();
        System.out.println("Libros de " + usuario2.getNombre() + ":");
        usuario2.mostrarLibros();

        biblioteca.devolverLibro("1111", 1);

        biblioteca.mostrarLibros();

        System.out.println("Libros de " + usuario1.getNombre() + ":");
        usuario1.mostrarLibros();
        System.out.println("Libros de " + usuario2.getNombre() + ":");
        usuario2.mostrarLibros();
    }
}