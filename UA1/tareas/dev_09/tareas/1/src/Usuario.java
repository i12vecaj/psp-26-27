import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Usuario de la biblioteca, con un máximo de tres préstamos simultáneos. */
public class Usuario {
    private final int id;
    private final String nombre;
    private final List<Libro> librosPrestados;

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
        this.librosPrestados = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Libro> getLibrosPrestados() { return Collections.unmodifiableList(librosPrestados); }

    public boolean prestarLibro(Libro libro) {
        if (libro == null || librosPrestados.size() >= 3 || librosPrestados.contains(libro)) {
            return false;
        }
        librosPrestados.add(libro);
        libro.prestar();
        return true;
    }

    public boolean devolverLibro(Libro libro) {
        if (libro == null || !librosPrestados.remove(libro)) {
            return false;
        }
        libro.devolver();
        return true;
    }

    public void mostrarLibros() {
        System.out.println("Libros prestados a " + nombre + " (ID " + id + "):");
        if (librosPrestados.isEmpty()) {
            System.out.println("  No tiene libros prestados.");
            return;
        }
        for (Libro libro : librosPrestados) {
            System.out.println("  " + libro.getTitulo() + " (ISBN " + libro.getIsbn() + ")");
        }
    }
}
