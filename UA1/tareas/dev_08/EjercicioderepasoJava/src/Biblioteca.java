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
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(isbn)) {
                return libros.get(i);
            }
        }
        return null;
    }

    public Usuario buscarUsuario(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public void prestarLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null) {
            System.out.println("Error: el libro con ISBN " + isbn + " no existe.");
            return;
        }
        if (usuario == null) {
            System.out.println("Error: el usuario con ID " + idUsuario + " no existe.");
            return;
        }
        if (libro.isPrestado()) {
            System.out.println("Error: el libro \"" + libro.getTitulo() + "\" ya está prestado.");
            return;
        }
        if (usuario.prestarLibro(libro)) {
            libro.prestar();
            System.out.println("Préstamo realizado: \"" + libro.getTitulo() + "\" a " + usuario.getNombre() + ".");
        }
    }

    public void devolverLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null) {
            System.out.println("Error: el libro con ISBN " + isbn + " no existe.");
            return;
        }
        if (usuario == null) {
            System.out.println("Error: el usuario con ID " + idUsuario + " no existe.");
            return;
        }
        if (usuario.devolverLibro(libro)) {
            libro.devolver();
            System.out.println("Devolución realizada: \"" + libro.getTitulo() + "\" por " + usuario.getNombre() + ".");
        }
    }

    public void mostrarLibros() {
        System.out.println("Libros de la biblioteca:");
        for (int i = 0; i < libros.size(); i++) {
            libros.get(i).mostrarInformacion();
        }
    }

    public void mostrarUsuarios() {
        System.out.println("Usuarios de la biblioteca:");
        for (int i = 0; i < usuarios.size(); i++) {
            usuarios.get(i).mostrarLibros();
        }
    }
}