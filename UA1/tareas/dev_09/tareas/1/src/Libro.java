import java.util.Objects;

/** Representa un libro del catálogo de la biblioteca. */
public class Libro {
    private final String isbn;
    private final String titulo;
    private final String autor;
    private boolean prestado;

    public Libro(String isbn, String titulo, String autor) {
        this.isbn = Objects.requireNonNull(isbn, "El ISBN no puede ser null");
        this.titulo = Objects.requireNonNull(titulo, "El título no puede ser null");
        this.autor = Objects.requireNonNull(autor, "El autor no puede ser null");
        this.prestado = false;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isPrestado() { return prestado; }

    public void prestar() { prestado = true; }
    public void devolver() { prestado = false; }

    public void mostrarInformacion() {
        System.out.println("ISBN: " + isbn + " | Título: " + titulo + " | Autor: " + autor
                + " | Estado: " + (prestado ? "Prestado" : "Disponible"));
    }
}
