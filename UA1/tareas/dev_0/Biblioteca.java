import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void añadirLibro(Libro libro) {
        libros.add(libro);
    }

    public void añadirUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Libro buscarLibro(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public void prestarLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null) {
            System.out.println("ERROR: El libro no existe.");
            return;
        }

        if (usuario == null) {
            System.out.println("ERROR: El usuario no existe.");
            return;
        }

        if (libro.isPrestado()) {
            System.out.println("ERROR: El libro ya está prestado.");
            return;
        }

        if (usuario != null && usuario.getId() == idUsuario) {
            usuario.prestarLibro(libro);
        }
    }

    public void devolverLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null || usuario == null) {
            System.out.println("ERROR: Libro o usuario no encontrado.");
            return;
        }

        usuario.devolverLibro(libro);
    }

    public void mostrarLibros() {
        System.out.println("Listado de libros:");
        for (Libro l : libros) {
            l.mostrarInformacion();
        }
    }
}
