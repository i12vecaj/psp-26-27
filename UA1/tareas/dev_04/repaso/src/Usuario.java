import java.util.ArrayList;

public class Usuario {
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

    public void prestarLibro(Libro libro) {
        if (librosPrestados.size() < 3) {
            librosPrestados.add(libro);
        }
    }

    public void devolverLibro(Libro libro) {
        librosPrestados.remove(libro);
    }

    public void mostrarLibros() {
        System.out.println("Libros de " + nombre + ":");
        for (Libro libro : librosPrestados) {
            libro.mostrarInformacion();
        }
    }
}
