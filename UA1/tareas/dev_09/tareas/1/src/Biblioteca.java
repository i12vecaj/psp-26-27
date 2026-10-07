import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Gestiona el catálogo y los usuarios de la biblioteca. */
public class Biblioteca {
    private final List<Libro> libros = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();

    public void anadirLibro(Libro libro) {
        Objects.requireNonNull(libro, "El libro no puede ser null");
        if (buscarLibroPorISBN(libro.getIsbn()) != null) {
            System.out.println("Error: ya existe un libro con ISBN " + libro.getIsbn() + ".");
            return;
        }
        libros.add(libro);
    }

    public void anadirUsuario(Usuario usuario) {
        Objects.requireNonNull(usuario, "El usuario no puede ser null");
        if (buscarUsuarioPorID(usuario.getId()) != null) {
            System.out.println("Error: ya existe un usuario con ID " + usuario.getId() + ".");
            return;
        }
        usuarios.add(usuario);
    }

    public Libro buscarLibroPorISBN(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) return libro;
        }
        return null;
    }

    public Usuario buscarUsuarioPorID(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) return usuario;
        }
        return null;
    }

    public void prestarLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Error: no existe ningún libro con ISBN " + isbn + ".");
            return;
        }
        Usuario usuario = buscarUsuarioPorID(idUsuario);
        if (usuario == null) {
            System.out.println("Error: no existe ningún usuario con ID " + idUsuario + ".");
            return;
        }
        if (libro.isPrestado()) {
            System.out.println("Error: el libro «" + libro.getTitulo() + "» ya está prestado.");
            return;
        }
        if (usuario.getLibrosPrestados().size() >= 3) {
            System.out.println("Error: " + usuario.getNombre() + " ya tiene el máximo de 3 libros.");
            return;
        }
        usuario.prestarLibro(libro);
        System.out.println("Préstamo realizado: «" + libro.getTitulo() + "» para " + usuario.getNombre() + ".");
    }

    public void devolverLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Error: no existe ningún libro con ISBN " + isbn + ".");
            return;
        }
        Usuario usuario = buscarUsuarioPorID(idUsuario);
        if (usuario == null) {
            System.out.println("Error: no existe ningún usuario con ID " + idUsuario + ".");
            return;
        }
        if (!usuario.devolverLibro(libro)) {
            System.out.println("Error: " + usuario.getNombre() + " no tiene prestado ese libro.");
            return;
        }
        System.out.println("Devolución realizada: «" + libro.getTitulo() + "».");
    }

    public void mostrarTodosLosLibros() {
        System.out.println("Catálogo de la biblioteca:");
        for (Libro libro : libros) libro.mostrarInformacion();
    }
}
