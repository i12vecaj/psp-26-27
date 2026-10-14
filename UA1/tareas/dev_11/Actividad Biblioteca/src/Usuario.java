import java.util.List;

public class Usuario {

    public int id;
    public String nombre;
    public List<Libro> libros;

    public Usuario(int id, String nombre, List<Libro> libros) {
        this.id = id;
        this.nombre = nombre;
        this.libros = libros;
    }

    public Usuario() {
    }

    public boolean prestarLibro(Libro libro) {
        if (libros.size() >= 3) {
            System.out.println("Error: ya tienes 3 libros, devuelve alguno antes de pedir otro");
            return false;
        }
        libros.add(libro);
        System.out.println("Prestado");
        return true;
    }

    public boolean devolverLibro(Libro libro) {
        if (libros.contains(libro)) {
            System.out.println("Libro devuelto");
            libros.remove(libro);
            return true;
        } else {
            System.out.println("Este libro= " + libro + ". No lo tenia el usuario " + this.nombre);
            return false;
        }



    }

    public void mostrarLibro(Usuario usuario) {
        for (Libro libro : usuario.libros) {
            System.out.println("Nombre del libro: " + libro.titulo + "\nAutor: " + libro.autor + "\n" + (libro.prestado ? "Esta prestado" : "No esta prestado" + "\n ISBN: " + libro.isbn));

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
