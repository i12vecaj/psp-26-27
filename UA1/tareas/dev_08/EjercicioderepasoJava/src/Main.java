public class Main {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        Libro l1 = new Libro("111", "El Quijote", "Cervantes");
        Libro l2 = new Libro("222", "La Celestina", "Fernando de Rojas");
        Libro l3 = new Libro("333", "Rayuela", "Julio Cortázar");
        Libro l4 = new Libro("444", "Cien años de soledad", "Gabriel García Márquez");

        Usuario u1 = new Usuario(1, "Ana");
        Usuario u2 = new Usuario(2, "Luis");

        biblioteca.anadirLibro(l1);
        biblioteca.anadirLibro(l2);
        biblioteca.anadirLibro(l3);
        biblioteca.anadirLibro(l4);

        biblioteca.anadirUsuario(u1);
        biblioteca.anadirUsuario(u2);

        biblioteca.mostrarLibros();

        biblioteca.prestarLibro("111", 1);
        biblioteca.prestarLibro("222", 1);
        biblioteca.prestarLibro("333", 2);

        biblioteca.prestarLibro("111", 2);

        u1.mostrarLibros();
        u2.mostrarLibros();

        biblioteca.devolverLibro("111", 1);

        biblioteca.mostrarLibros();
        biblioteca.mostrarUsuarios();
    }
}