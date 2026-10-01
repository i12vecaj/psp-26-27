public class Libro {
    String isbn;
    String titulo;
    String autor;
    boolean prestado=false;

    public Libro(String isbn, String titulo, String autor){
        this.isbn=isbn;
        this.titulo=titulo;
        this.autor=autor;
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

    public void Prestar(){
       this.prestado=true;
       if(prestado=false){
           System.out.println("Este libro está prestado");
       }
    }

    public void Devolver(boolean prestado){
        this.prestado=prestado;
    }

    public void MostrarInformacion(String isbn, String titulo, String autor, boolean prestado){
        System.out.println("Los datos: "+ getIsbn()+"| |"+getAutor()+"| |"+getClass()+"| |"+isPrestado());
    }
}
