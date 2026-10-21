import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void anadirLibro(Libro libro) {
        libros.add(libro);
    }

    public void anadirUsuario(Usuario usuario) {
        usuarios.add(usuario);
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
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null) {
            System.out.println("Error: el libro no existe");
            return;
        }
        if (usuario == null) {
            System.out.println("Error: el usuario no existe");
            return;
        }
        if (libro.isPrestado()) {
            System.out.println("Error: el libro " + libro.getTitulo() + " ya está prestado");
            return;
        }
        if (usuario.getLibrosPrestados().size() >= 3) {
            System.out.println("Error: " + usuario.getNombre() + " ya tiene 3 libros prestados");
            return;
        }

        usuario.prestarLibro(libro);
        libro.prestar();
        System.out.println("Libro " + libro.getTitulo() + " prestado a " + usuario.getNombre());
    }

    public void devolverLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null) {
            System.out.println("Error: el libro no existe");
            return;
        }
        if (usuario == null) {
            System.out.println("Error: el usuario no existe");
            return;
        }

        usuario.devolverLibro(libro);
        libro.devolver();
        System.out.println("Libro " + libro.getTitulo() + " devuelto por " + usuario.getNombre());
    }

    public void mostrarLibros() {
        for (Libro libro : libros) {
            libro.mostrarInformacion();
        }
    }
}
