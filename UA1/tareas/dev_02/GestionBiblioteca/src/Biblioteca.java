import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Libro> libros;
    private List<Usuario> usuarios;

    public Biblioteca(String nombre) {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void addLibro(Libro libro) {
        libros.add(libro);
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                return l;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public void prestarLibro(String isbn, int id) {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Error: El libro con ISBN " + isbn + " no existe.");
            return;
        }
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            System.out.println("Error: El usuario con ID " + id + " no existe.");
            return;
        }
        if (libro.isPrestado()) {
            System.out.println("Error: El libro \"" + libro.getTitulo() + "\" ya está prestado.");
            return;
        }
        if (usuario.getLibros().size() >= 3) {
            System.out.println("Error: El usuario \"" + usuario.getNombre() + "\" ya tiene 3 libros prestados.");
            return;
        }
        libro.setPrestado(true);
        usuario.prestarLibro(libro);
        System.out.println("Préstamo exitoso: El libro " + libro.getTitulo() + "ha sido prestado a " + usuario.getNombre() + ".");
    }
    public void devolverLibro(String isbn, int id) {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Error: El libro con ISBN " + isbn + " no existe.");
            return; }
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            System.out.println("Error: El usuario con ID " + id + " no existe.");
            return;
        }
        if (usuario.devolverLibro(libro)) {
            libro.setPrestado(false);
            System.out.println("Devolución exitosa: El usuario " + usuario.getNombre() + " ha devuelto \"" + libro.getTitulo() + "\".");
        } else {
            System.out.println("Error: El usuario no tenía prestado este libro.");
        }
    }
    public void mostrarTodosLosLibros() {
        System.out.println("--- Lista de Libros ---");
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            for (Libro l : libros) {
                System.out.println(l);
            }
        }
    }
    public void mostrarEstadoFinal() {

        System.out.println("     ESTADO FINAL DE LA BIBLIOTECA        ");
        System.out.println("==========================================");

        // 1. Muestra el estado actual de la lista general de libros
        mostrarTodosLosLibros();

        // 2. Muestra los libros que tiene cada usuario en este momento
        System.out.println("--- Libros por Usuario ---");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("Usuario: " + u.getNombre() + " (ID: " + u.getId() + ")");
                if (u.getLibros().isEmpty()) {
                    System.out.println("  -> No tiene libros prestados.");
                } else {
                    for (Libro l : u.getLibros()) {
                        System.out.println("  - [ISBN: " + l.getIsbn() + "] " + l.getTitulo());
                    }
                }
            }
        }
      }
    }