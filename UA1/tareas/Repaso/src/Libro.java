public class Libro{
    private String isbn;
    private String titulo;
    private String autor;
    private boolean prestado = false;

    public Libro(boolean prestado, String autor, String titulo, String isbn) {
        this.prestado = prestado;
        this.autor = autor;
        this.titulo = titulo;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public void prestar(){
        prestado = true;

    }

    public void devolver(){
        prestado = false;

    }

    @Override
    public String toString() {
        return "libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", prestado=" + prestado +
                '}';
    }

    public String mostrarInformacion(){
        return toString();
    }
}
