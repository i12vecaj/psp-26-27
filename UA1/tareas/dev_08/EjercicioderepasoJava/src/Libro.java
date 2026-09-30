public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private boolean prestado;

    public Libro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = false;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void prestar() {
        prestado = true;
    }

    public void devolver() {
        prestado = false;
    }

    public void mostrarInformacion() {
        String estado = prestado ? "Prestado" : "Disponible";
        System.out.println("ISBN: " + isbn + " | Título: " + titulo
                + " | Autor: " + autor + " | Estado: " + estado);
    }
}