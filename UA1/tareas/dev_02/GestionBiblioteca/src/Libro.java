public class Libro {
    String isbn;
    String titulo;
    String autor;
    boolean prestado;

    public Libro(String isbn, String titulo, String autor, boolean prestado) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = prestado;
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
    public boolean prestar (Libro libro){
        if (!libro.prestado) {
            libro.prestado=true;
            System.out.println("Libro prestado");
        } else {
            System.out.println("Ese libro ya está prestado");
        }
        return false;
    }
    public boolean devolver (Libro libro){
        if (libro.prestado){
            libro.prestado=false;
            System.out.println("Libro devuelto");
            return true;
        } else {
            System.out.println("Este libro no estaba prestado");}

        return false;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", prestado=" + prestado +
                '}';
    }

    public String MostrarInformacion(Libro libro){
        return libro.toString();
    }
}

