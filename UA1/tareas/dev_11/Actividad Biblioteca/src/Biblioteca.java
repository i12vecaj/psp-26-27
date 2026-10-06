import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    List<Libro> libros = new ArrayList<>();
    List<Usuario> usuarios = new ArrayList<>();

    public Biblioteca() {
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void añadirLibro(String isbn, String titulo, String autor) {
        Libro libro = new Libro(isbn, titulo, autor);
        libros.add(libro);
        System.out.println("Libro " + titulo + " Añadido con exito");

    }

    public void añadirUsuario(int id, String nombre) {
        usuarios.add(new Usuario(id, nombre, new ArrayList<>()));
        System.out.println("Usuario " + nombre + " creado con éxito");
    }

    public Libro buscarPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public Usuario buscarPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public boolean prestarLibro(String isbn, int idUsuario) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Error: no existe ningún libro con ISBN " + isbn);
            return false;
        }

        Usuario usuario = buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Error: no existe ningún usuario con id " + idUsuario);
            return false;
        }

        if (libro.isPrestado()) {
            System.out.println("Error: el libro " + libro.getTitulo() + " ya está prestado");
            return false;
        }

        if (usuario.prestarLibro(libro)) {
            libro.setPrestado(true);
            return true;
        }
        return false;
    }

    public boolean devolverLibro(String isbn, int idUsuario) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Error: no existe ningún libro con ISBN " + isbn);
            return false;
        }

        Usuario usuario = buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Error: no existe ningún usuario con id " + idUsuario);
            return false;
        }

        if (usuario.devolverLibro(libro)) {
            libro.setPrestado(false);
            return true;
        }
        return false;
    }

    public void mostrarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca");
            return;
        }
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }}