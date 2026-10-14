/**
 * @file      Usuario.java
 * @brief     Usuario de la biblioteca con su lista de libros prestados (como mucho 3)
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.repaso;

import java.util.ArrayList;

public class Usuario {

  public static final int MAXIMO_LIBROS = 3;

  private final int id;
  private final String nombre;
  private final ArrayList<Libro> librosPrestados;

  public Usuario(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
    this.librosPrestados = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  // devuelvo una copia para que nadie cambie la lista desde fuera
  public ArrayList<Libro> getLibrosPrestados() {
    return new ArrayList<>(librosPrestados);
  }

  public boolean tieneElMaximo() {
    return librosPrestados.size() >= MAXIMO_LIBROS;
  }

  // devuelve false si ya tiene 3 libros
  public boolean prestarLibro(Libro libro) {
    if (libro == null || tieneElMaximo()) {
      return false;
    }
    librosPrestados.add(libro);
    return true;
  }

  // devuelve false si el usuario no tenía ese libro
  public boolean devolverLibro(Libro libro) {
    return librosPrestados.remove(libro);
  }

  public void mostrarLibros() {
    System.out.println("Libros de " + nombre + " (id " + id + "): "
        + librosPrestados.size() + " de " + MAXIMO_LIBROS);
    if (librosPrestados.isEmpty()) {
      System.out.println("  No tiene ningún libro prestado");
    }
    for (Libro libro : librosPrestados) {
      libro.mostrarInformacion();
    }
  }
}
