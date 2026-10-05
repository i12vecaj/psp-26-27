import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private List<String> misprestados;

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.misprestados = new ArrayList<>();
    }

    public void prestarLibro(Libro libro) {
        libro.prestar();
        String tit = libro.getTitulo();
        misprestados.add(tit);
    }

    public void devolverLibro(Libro libro) {
        libro.devolver();
        misprestados.remove(libro.getTitulo());
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getMisprestados() {
        return misprestados;
    }

    public void mostrarLibros() {
        System.out.println("Libros de "+getNombre()+":");
        misprestados.forEach(System.out::println);
        System.out.println("===========================");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", misprestados=" + misprestados +
                '}';
    }
}
