/**
 * @file      Libro.java
 * @brief     Libro de la biblioteca con ISBN, título, autor y si está prestado o no
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.repaso;

public class Libro {

  private final String isbn;
  private final String titulo;
  private final String autor;
  private boolean prestado;

  // un libro nuevo siempre empieza disponible
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
    String estado = prestado ? "prestado" : "disponible";
    System.out.println("  [" + isbn + "] " + titulo + " de " + autor + " (" + estado + ")");
  }
}
