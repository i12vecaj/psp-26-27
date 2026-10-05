public class Main {
    static void main() {

        // CREANDO UNA BIBLIOTECA
        Biblioteca biblio = new Biblioteca();

        // CREAR 4 LIBROS
        Libro libro1 = new Libro("1234", "J.R.R. Tolkien","El Señor de los Anillos");
        Libro libro2 = new Libro("1235",  "Gabriel García Márquez","Cien Años de Soledad");
        Libro libro3 = new Libro("1236",  "George Orwell","1984");
        Libro libro4 = new Libro("1237", "Frank Herbert","Dune");

        // CREAR 2 USUARIOS
        Usuario usuario1 = new Usuario(1,"Pablo");
        Usuario usuario2 = new Usuario(2,"Ian");

        // AÑADIR TODO A LA BIBLIO
        biblio.anadirLibro(libro1);
        biblio.anadirLibro(libro2);
        biblio.anadirLibro(libro3);
        biblio.anadirLibro(libro4);

        biblio.anadirUsuario(usuario1);
        biblio.anadirUsuario(usuario2);

        // MOSTRAR TODOS LOS LIBROS
        biblio.mostrarLosLibros();

        // PRESTAR DOS LIBROS AL USUARIO 1
        biblio.prestarLibro(usuario1,libro1);
        biblio.prestarLibro(usuario1,libro2);

        // PRESTAR UN LIBRO AL USUARIO 2
        biblio.prestarLibro(usuario2,libro3);
        System.out.println();

        // INTENTAR PRESTAR UN LIBRO PRESTADO
        biblio.prestarLibro(usuario2,libro1);

        // MOSTRAR LIBROS DE CADA USUARIO
        usuario1.mostrarLibros();
        usuario2.mostrarLibros();

        // MOSTRAR TODO
        biblio.mostrarLosLibros();
        libro1.mostrarInformacion();
        libro2.mostrarInformacion();
        libro3.mostrarInformacion();
        libro4.mostrarInformacion();
        System.out.println(usuario1);
        System.out.println(usuario2);
    }
}
