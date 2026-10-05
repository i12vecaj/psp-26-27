import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Biblioteca {
    private List<Libro> libros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void anadirLibro(Libro libro){
        libros.add(libro);
    }

    public void anadirUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Libro buscarPorIsbn(String isbn) {
        return libros.stream()
                .filter(libro -> Objects.equals(libro.getIsbn(), isbn))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorID(int id) {
        return usuarios.stream()
                .filter(usuario -> Objects.equals(usuario.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void prestarLibro(Usuario user, Libro lib) {

        if (user == null || lib == null ) {
            System.out.println("No se encuentra al usuario/libro");
            return;
        }

        if (lib.isPrestado()) {
            System.out.println("Este libro ya ha sido prestado");
            return;
        }

        if (user.getMisprestados().size() == 3) {
            System.out.println("Maximo de libros prestados");
            return;
        }

        try {
            user.prestarLibro(lib);
            System.out.println("Libro prestado: "+lib.getTitulo());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void devolverLibro(int id, String isbn) {
            Usuario usuario = buscarPorID(id);
            Libro libro = buscarPorIsbn(isbn);

            if (usuario != null && libro != null) {
                usuario.devolverLibro(libro);
                System.out.println("Libro devuelto");
            } else {
                System.out.println("No se ha encontrado el usuario/libro");
            }
    }

    public void mostrarLosLibros() {
        System.out.println("Listado de libros de la biblioteca:");
        for (Libro libro : libros) {
            System.out.println("- "+libro.getTitulo());
        }
        System.out.println("===================================");
    }


}
