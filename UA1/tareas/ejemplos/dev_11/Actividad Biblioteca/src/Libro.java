public class Libro {
    public String isbn;
    public String titulo;
    public String autor;
    public boolean prestado;

    /**
     * Metodo para prestar un libro
     * @param libro
     * @return devuelve un booleano de si se ha podido realizar o no
     */
    public boolean prestar(Libro libro) {
        if (!libro.prestado){
            libro.prestado=false;
            System.out.println("Prestado con elegancia");
            return true;
        }else{
            System.out.println("Ese libro no esta disponible para prestar");
            return false;}

    }
    /**
     * Metodo para devolver un libro
     * @param libro
     * @return devuelve un booleano de si se ha podido realizar o no
     */
    public boolean devolver(Libro libro) {
        if (libro.prestado){
            libro.prestado=false;
            System.out.println("Devuelto con elegancia");
            return true;
        }else{
            System.out.println("Ese libro no estaba prestado");
            return false;}

    }

    public Libro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = false;
    }

    public Libro() {
    }

    /**
     * Muestra por pantalla la informacion sobre el libro
     * @param libro
     */
    public void mostrarInformacion(Libro libro) {
        System.out.println("Nombre del libro: " + libro.titulo + "\nAutor: " + libro.autor + "\n" + (prestado ? "Esta prestado" : "No esta prestado" + "\n ISBN: " + libro.isbn));
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
    public String toString() {
        return "ISBN: " + isbn + " | " + titulo + " de " + autor
                + " | " + (prestado ? "Prestado" : "Disponible");
    }
}
