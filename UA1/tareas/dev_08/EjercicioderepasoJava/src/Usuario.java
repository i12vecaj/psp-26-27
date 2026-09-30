import java.util.ArrayList;

public class Usuario {

    private static final int MAX_LIBROS = 3;

    private int id;
    private String nombre;
    private ArrayList<Libro> librosPrestados;

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.librosPrestados = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    public boolean prestarLibro(Libro libro) {
        if (librosPrestados.size() >= MAX_LIBROS) {
            System.out.println("Error: " + nombre + " ya tiene " + MAX_LIBROS + " libros prestados.");
            return false;
        }
        librosPrestados.add(libro);
        return true;
    }

    public boolean devolverLibro(Libro libro) {
        for (int i = 0; i < librosPrestados.size(); i++) {
            if (librosPrestados.get(i).getIsbn().equals(libro.getIsbn())) {
                librosPrestados.remove(i);
                return true;
            }
        }
        System.out.println("Error: " + nombre + " no tiene el libro " + libro.getTitulo() + ".");
        return false;
    }

    public void mostrarLibros() {
        System.out.println("Libros de " + nombre + " (ID " + id + "):");
        if (librosPrestados.isEmpty()) {
            System.out.println("  No tiene libros prestados.");
            return;
        }
        for (int i = 0; i < librosPrestados.size(); i++) {
            System.out.print("  - ");
            librosPrestados.get(i).mostrarInformacion();
        }
    }
}