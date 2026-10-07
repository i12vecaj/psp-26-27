/*
Crea una clase Usuario con:

id (int)
nombre (String)
Una lista de libros prestados.

Debe incluir:

Constructor.
Getters.
Método prestarLibro(Libro libro).
Método devolverLibro(Libro libro).
Método mostrarLibros().

Un usuario no puede tener más de 3 libros prestados.
*/
import java.util.ArrayList;


public class Usuario {

    private int id;
    private String nombre;
    private arrayList<Libros> lista;

    public Usuario(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
        this.lista = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public ArrayList<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    public prestarLibro(Libro libro){
        if (lista.size() < 3) {
            lista.add(libro);
            libro.prestar;
        }
    }

    public void devolverLibro(Libro libro) {
        if (lista.contains(libro)) {
            lista.remove(libro);
            libro.devolver();
        }
    }

    public void mostrarLibros() {
        System.out.println("Libros de " + nombre + ":");

        if (lista.isEmpty()) {
            System.out.println("No tiene libros prestados.");
        } else {
            for (Libro libro : lista) {
                System.out.println("- " + libro.getTitulo());
            }
        }
    }
}