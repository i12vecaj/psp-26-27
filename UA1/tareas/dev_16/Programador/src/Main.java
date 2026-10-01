public class Main {
    static void main() {
        Biblioteca b1=new Biblioteca();
        Libro l1=new Libro("978-84-376-0494-7","Don Quijote de la Mancha","Miguel de Cervantes");
        Libro l2=new Libro("978-84-206-5131-6","Cien años de soledad","Gabriel García Márquez");
        Libro l3=new Libro("978-84-233-4365-3","La sombra del viento","Carlos Ruiz Zafón");
        Libro l4=new Libro("978-84-9759-219-6","1984","George Orwell");
        Usuario u1=new Usuario(1,"Manolo");
        Usuario u2=new Usuario(2,"Fran");

        b1.anadirLibro(l1);
        b1.anadirLibro(l2);
        b1.anadirLibro(l3);
        b1.anadirLibro(l4);
        b1.anadirUsuario(u1);
        b1.anadirUsuario(u2);

        b1.mostrarLibros();
        b1.prestarLibro("978-84-376-0494-7",1);
        b1.prestarLibro("978-84-206-5131-6",1);
        b1.prestarLibro("978-84-233-4365-3",2);

        b1.prestarLibro("978-84-233-4365-3",1);

        b1.mostrarLibrosUsuario(1);
        b1.mostrarLibrosUsuario(2);
        b1.devolverLibro("978-84-376-0494-7");

        b1.mostrarEstado();


    }
}
