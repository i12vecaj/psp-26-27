import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private static final int MAX_PRESTAMOS = 3;

    private List<Libro> libros = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    // Libros que tiene prestados cada usuario (clave: id del usuario)
    private Map<Integer, List<Libro>> prestamos = new HashMap<>();

    public void anadirLibro(Libro libro) {
        libros.add(libro);
    }

    public void anadirUsuario(Usuario usuario) {
        usuarios.add(usuario);
        prestamos.put(usuario.getId(), new ArrayList<>());
    }

    public Libro buscarLibro(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public void prestarLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        if (libro == null) {
            System.out.println("Error: no existe ningún libro con ISBN " + isbn);
            return;
        }

        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("Error: no existe ningún usuario con ID " + idUsuario);
            return;
        }

        if (libro.isPrestado()) {
            System.out.println("Error: el libro \"" + libro.getTitulo() + "\" ya está prestado");
            return;
        }

        List<Libro> librosUsuario = prestamos.get(idUsuario);
        if (librosUsuario.size() >= MAX_PRESTAMOS) {
            System.out.println("Error: " + usuario.getNombre() + " ya tiene " + MAX_PRESTAMOS + " libros prestados");
            return;
        }

        libro.setPrestado(true);
        librosUsuario.add(libro);
        System.out.println("Libro \"" + libro.getTitulo() + "\" prestado a " + usuario.getNombre());
    }

    public void devolverLibro(String isbn) {
        Libro libro = buscarLibro(isbn);
        if (libro == null) {
            System.out.println("Error: no existe ningún libro con ISBN " + isbn);
            return;
        }

        if (!libro.isPrestado()) {
            System.out.println("Error: el libro \"" + libro.getTitulo() + "\" no está prestado");
            return;
        }

        for (List<Libro> librosUsuario : prestamos.values()) {
            librosUsuario.remove(libro);
        }
        libro.setPrestado(false);
        System.out.println("Libro \"" + libro.getTitulo() + "\" devuelto");
    }

    public void mostrarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca");
            return;
        }
        for (Libro libro : libros) {
            System.out.println(libro.getIsbn() + " | " + libro.getTitulo() + " | " + libro.getAutor()
                    + " | " + (libro.isPrestado() ? "Prestado" : "Disponible"));
        }
    }

    public void mostrarLibrosUsuario(int idUsuario) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("Error: no existe ningún usuario con ID " + idUsuario);
            return;
        }

        List<Libro> librosUsuario = prestamos.get(idUsuario);
        if (librosUsuario.isEmpty()) {
            System.out.println(usuario.getNombre() + " no tiene libros prestados");
            return;
        }

        System.out.println("Libros de " + usuario.getNombre() + ":");
        for (Libro libro : librosUsuario) {
            System.out.println(libro.getIsbn() + " | " + libro.getTitulo() + " | " + libro.getAutor());
        }
    }

    public void mostrarEstado() {
        System.out.println("ESTADO DE LA BIBLIOTECA: ");
        System.out.println("Libros");
        mostrarLibros();

        System.out.println("Usuarios");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        }
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.getId() + " | " + usuario.getNombre() + " | "
                    + prestamos.get(usuario.getId()).size() + " libro(s) prestado(s)");
            mostrarLibrosUsuario(usuario.getId());
        }
    }
}
