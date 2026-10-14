/**
 * @file      Biblioteca.java
 * @brief     Guarda los libros y los usuarios y gestiona los préstamos y las devoluciones
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.repaso;

import java.util.ArrayList;

public class Biblioteca {

  private final ArrayList<Libro> libros;
  private final ArrayList<Usuario> usuarios;

  public Biblioteca() {
    libros = new ArrayList<>();
    usuarios = new ArrayList<>();
  }

  // no se añade un libro nulo ni uno con un ISBN repetido
  public boolean anadirLibro(Libro libro) {
    if (libro == null || buscarLibro(libro.getIsbn()) != null) {
      System.out.println("Error: no se puede añadir ese libro");
      return false;
    }
    libros.add(libro);
    return true;
  }

  // no se añade un usuario nulo ni uno con un id repetido
  public boolean anadirUsuario(Usuario usuario) {
    if (usuario == null || buscarUsuario(usuario.getId()) != null) {
      System.out.println("Error: no se puede añadir ese usuario");
      return false;
    }
    usuarios.add(usuario);
    return true;
  }

  // devuelve null si no lo encuentra
  public Libro buscarLibro(String isbn) {
    if (isbn == null) {
      return null;
    }
    for (Libro libro : libros) {
      // los String se comparan con equals y no con ==
      if (libro.getIsbn().equals(isbn)) {
        return libro;
      }
    }
    return null;
  }

  // devuelve null si no lo encuentra
  public Usuario buscarUsuario(int id) {
    for (Usuario usuario : usuarios) {
      if (usuario.getId() == id) {
        return usuario;
      }
    }
    return null;
  }

  public boolean prestarLibro(String isbn, int idUsuario) {
    Libro libro = buscarLibro(isbn);
    Usuario usuario = buscarUsuario(idUsuario);

    if (libro == null) {
      System.out.println("Error: no existe ningún libro con ISBN " + isbn);
      return false;
    }
    if (usuario == null) {
      System.out.println("Error: no existe ningún usuario con id " + idUsuario);
      return false;
    }
    if (libro.isPrestado()) {
      System.out.println("Error: «" + libro.getTitulo() + "» ya está prestado");
      return false;
    }
    if (usuario.tieneElMaximo()) {
      System.out.println("Error: " + usuario.getNombre() + " ya tiene "
          + Usuario.MAXIMO_LIBROS + " libros");
      return false;
    }

    libro.prestar();
    usuario.prestarLibro(libro);
    System.out.println("Prestado «" + libro.getTitulo() + "» a " + usuario.getNombre());
    return true;
  }

  public boolean devolverLibro(String isbn, int idUsuario) {
    Libro libro = buscarLibro(isbn);
    Usuario usuario = buscarUsuario(idUsuario);

    if (libro == null) {
      System.out.println("Error: no existe ningún libro con ISBN " + isbn);
      return false;
    }
    if (usuario == null) {
      System.out.println("Error: no existe ningún usuario con id " + idUsuario);
      return false;
    }
    if (!usuario.devolverLibro(libro)) {
      System.out.println("Error: " + usuario.getNombre() + " no tiene «" + libro.getTitulo() + "»");
      return false;
    }

    libro.devolver();
    System.out.println("Devuelto «" + libro.getTitulo() + "» por " + usuario.getNombre());
    return true;
  }

  public void mostrarLibros() {
    System.out.println("Libros de la biblioteca:");
    for (Libro libro : libros) {
      libro.mostrarInformacion();
    }
  }

  public void mostrarUsuarios() {
    for (Usuario usuario : usuarios) {
      usuario.mostrarLibros();
    }
  }
}
