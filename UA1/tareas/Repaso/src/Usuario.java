import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private List<String> misPrestados;

    public Usuario(int id, String nombre, List<String> misprestados) {
        this.id = id;
        this.nombre = nombre;
        this.misPrestados = misprestados;
    }

    public void prestarLibro(Libro libro) {
        libro.prestar();
        String titutlo = libro.getTitulo();
        misPrestados.add(titutlo);
    }

    public void devolverLibro(Libro libro) {
        libro.devolver();
        misPrestados.remove(libro.getTitulo());
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getMisprestados() {
        return misPrestados;
    }

    public void mostrarLibros() {
        misPrestados.forEach(System.out::println);
    }
}