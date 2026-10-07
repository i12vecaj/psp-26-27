/*
# Ejercicio de repaso Java 2º DAM: Gestión de una biblioteca
## Objetivo
Realizar una aplicación Java para gestionar una biblioteca, poniendo en práctica conceptos
fundamentales de Programación: clases, objetos, encapsulación, constructores, métodos, `ArrayList`,
bucles, condicionales, búsquedas y relaciones entre clases.

## 1. Clase `Libro`

Crea una clase `Libro` con los siguientes atributos privados:

- `isbn` (`String`)
- `titulo` (`String`)
- `autor` (`String`)
- `prestado` (`boolean`)

Debe incluir:

- Constructor con `isbn`, `titulo` y `autor`.
- Getters necesarios.
- Método `prestar()` para marcar el libro como prestado.
- Método `devolver()` para marcarlo como disponible.
- Método `mostrarInformacion()` para mostrar sus datos.

Un libro se crea inicialmente como disponible.
*/
import java.util.ArrayList;

public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private boolean prestado;

    public Libro(String isbn, String titulo, String autor){
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = false;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void prestar(){
        prestado = true;
    }

    public void devuelto(){
        prestado = false;
    }

    public void mostrarInformacion() {
        System.out.println("ISBN: " + isbn);
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);

        if (prestado) {
            System.out.println("Estado: Prestado");
        } else {
            System.out.println("Estado: Disponible");
        }
    }
}
}