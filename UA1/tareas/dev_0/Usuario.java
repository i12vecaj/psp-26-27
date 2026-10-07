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

    public void prestarLibro(Libro libro) {
        if (librosPrestados.size() >= 3) {
            System.out.println("El usuario " + nombre + " ya tiene 3 libros prestados.");
            return;
        }

        librosPrestados.add(libro);
        libro.prestar();
        System.out.println("Libro prestado a " + nombre + ": " + libro.getTitulo());
    }

    public void devolverLibro(Libro libro) {
        if (librosPrestados.remove(libro)) {
            libro.devolver();
            System.out.println("Libro devuelto por " + nombre + ": " + libro.getTitulo());
        } else {
            System.out.println("El usuario " + nombre + " no tenía este libro.");
        }
    }

    public void mostrarLibros() {
        System.out.println("Libros de " + nombre + ":");
        for (Libro l : librosPrestados) {
            System.out.println(" - " + l.getTitulo());
        }
    }
}
