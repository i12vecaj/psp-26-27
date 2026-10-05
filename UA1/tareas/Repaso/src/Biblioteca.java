import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Libro> libros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void añadirLibro(Libro libro) {
        libros.add(libro);
        System.out.println("Libro añadido " + libro.getTitulo());
    }

    public void añadirUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario añadido " + usuario.getNombre());
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
            System.out.println("Error");
            return;
        }

        if (usuario == null) {
            System.out.println("Error");
            return;
        }

        if (libro.isPrestado()) {
            System.out.println("Esta Prestado ya");
            return;
        }

        if (usuario.getMisprestados().size() >= 3) {
            System.out.println("El usuario " + usuario.getNombre() + " ya tiene 3 libros");
            return;
        }

        usuario.prestarLibro(libro);
        System.out.println("El libro " + libro.getTitulo() + " ha sido prestado a " + usuario.getNombre());
    }

    public void devolverLibro(String isbn, int idUsuario) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(idUsuario);

        if (libro == null || usuario == null) {
            System.out.println("Error");
            return;
        }

        if (!libro.isPrestado()) {
            System.out.println("El libro no estaba prestado");
            return;
        }

        usuario.devolverLibro(libro);
        System.out.println("El libro '" + libro.getTitulo() + "' ha sido devuelto por " + usuario.getNombre()  );
    }

    public void mostrarLibros() {
        if (libros.isEmpty()) {
            System.out.println("La biblioteca no tiene libr");
            return;
        }

        System.out.println("Lista de Libros en la Biblioteca");
        for (Libro libro : libros) {
            System.out.println(libro.mostrarInformacion());
        }

    }
}