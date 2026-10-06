public class Main {
    public static void main(String[] args) {
        // 1. Crear una biblioteca
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central");


        Libro libro1 = new Libro("978-01", "Cien años de soledad", "Gabriel García Márquez", false);
        Libro libro2 = new Libro("978-02", "Don Quijote de la Mancha", "Miguel de Cervantes", false);
        Libro libro3 = new Libro("978-03", "1984", "George Orwell", false);
        Libro libro4 = new Libro("978-04", "El Principito", "Antoine de Saint-Exupéry", false);


        Usuario usuario1 = new Usuario("Lucía Gómez", 001);
        Usuario usuario2 = new Usuario("Carlos Ruiz", 002);


        biblioteca.addLibro(libro1);
        biblioteca.addLibro(libro2);
        biblioteca.addLibro(libro3);
        biblioteca.addLibro(libro4);

        biblioteca.addUsuario(usuario1);
        biblioteca.addUsuario(usuario2);

        String tituloBuscado = "1984";
        if (tituloBuscado.equals(libro3.getTitulo())) {
            // Demostración de uso de equals()
        }

        biblioteca.mostrarTodosLosLibros();

       System.out.println("Operación: Prestar dos libros al Usuario 1");
        usuario1.prestarLibro(libro1);
        usuario1.prestarLibro(libro2);


        System.out.println(" Operación: Prestar un libro al Usuario 2 ");
        usuario2.prestarLibro(libro3);


        System.out.println(" Operación: Intentar prestar un libro ya prestado ");
        usuario2.prestarLibro(libro1);

        biblioteca.mostrarTodosLosLibros();

         System.out.println("Operación: Devolver un libro");
        usuario1.devolverLibro(libro1);

        biblioteca.mostrarEstadoFinal();
    }
}