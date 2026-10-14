/**
 * @file      Main.java
 * @brief     Ejercicio de repaso de Java: prueba la biblioteca siguiendo los 11 pasos del enunciado
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.repaso;

public class Main {

  public static void main(String[] args) {
    // 1. crear una biblioteca
    Biblioteca biblioteca = new Biblioteca();

    // 2. crear 4 libros
    Libro quijote = new Libro("978-84-376-0494-7", "Don Quijote de la Mancha", "Miguel de Cervantes");
    Libro cien = new Libro("978-84-376-0495-4", "Cien años de soledad", "Gabriel García Márquez");
    Libro lazarillo = new Libro("978-84-376-0496-1", "El Lazarillo de Tormes", "Anónimo");
    Libro celestina = new Libro("978-84-376-0497-8", "La Celestina", "Fernando de Rojas");

    // 3. crear 2 usuarios
    Usuario ana = new Usuario(1, "Ana");
    Usuario luis = new Usuario(2, "Luis");

    // 4. añadirlos a la biblioteca
    biblioteca.anadirLibro(quijote);
    biblioteca.anadirLibro(cien);
    biblioteca.anadirLibro(lazarillo);
    biblioteca.anadirLibro(celestina);
    biblioteca.anadirUsuario(ana);
    biblioteca.anadirUsuario(luis);

    // 5. mostrar todos los libros
    titulo("5. Todos los libros");
    biblioteca.mostrarLibros();

    // 6. prestar dos libros al usuario 1
    titulo("6. Dos libros para el usuario 1");
    biblioteca.prestarLibro(quijote.getIsbn(), 1);
    biblioteca.prestarLibro(cien.getIsbn(), 1);

    // 7. prestar un libro al usuario 2
    titulo("7. Un libro para el usuario 2");
    biblioteca.prestarLibro(lazarillo.getIsbn(), 2);

    // 8. intentar prestar un libro que ya está prestado
    titulo("8. Libro que ya está prestado");
    biblioteca.prestarLibro(quijote.getIsbn(), 2);

    // 9. mostrar los libros de cada usuario
    titulo("9. Libros de cada usuario");
    biblioteca.mostrarUsuarios();

    // 10. devolver un libro
    titulo("10. Devolución");
    biblioteca.devolverLibro(quijote.getIsbn(), 1);

    // 11. estado final de la biblioteca y de los usuarios
    titulo("11. Estado final");
    biblioteca.mostrarLibros();
    biblioteca.mostrarUsuarios();
  }

  private static void titulo(String texto) {
    System.out.println();
    System.out.println("== " + texto);
  }
}
