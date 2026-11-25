import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public int id;
    public String nombre;
    public List <Libro> libros = new ArrayList<>();

    public Usuario(String nombre, int id) {
        this.id = id;
        this.nombre = nombre;
        }
    public boolean prestarLibro (Libro libro){
        if (!libro.prestado){
        libro.prestado=true;
        libros.add(libro);
        return true;} else { return false;}
    }
    public boolean devolverLibro (Libro libro){
        if (libro.prestado){
            if (libros.size()>=3){
                System.out.println("ya tienes 3 libros prestados, es el máximo");
                return false;}
            System.out.println("Tu libro ha sido devuelto");
            libro.prestado=false;
            return true;} else { return false;}

    }
    public void mostrarLibro (Usuario usuario){
        for (Libro libro : usuario.libros){
            System.out.println("Nombre del libro");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
