public class Libro {

    // ATRIBUTOS

    private String isbn;
    private String autor;
    private String titulo;
    private boolean prestado;

    // METODOS

    public Libro(String isbn, String autor, String titulo) {
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;
        this.prestado = false;
    }

    public void prestar() {
        if (prestado) {
            System.out.println("El libro ya ha sido prestado");
        } else {
            System.out.println("Prestando...");
            prestado = true;
        }
    }

    public void devolver() {
        prestado = false;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isPrestado() {
        return prestado;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", autor='" + autor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", prestado=" + prestado +
                '}';
    }

    public void mostrarInformacion() {
        System.out.println(toString());
    }
}
