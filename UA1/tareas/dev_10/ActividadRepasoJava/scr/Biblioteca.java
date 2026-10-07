/*
3. Clase Biblioteca

Crea una clase Biblioteca que almacene:

Una lista de libros.
Una lista de usuarios.

Debe implementar métodos para:

Añadir un libro.
Añadir un usuario.
Buscar un libro por ISBN.
Buscar un usuario por ID.
Prestar un libro a un usuario.
Devolver un libro.
Mostrar todos los libros.

Al prestar:

Si el libro no existe, mostrar un mensaje de error.
Si el usuario no existe, mostrar un mensaje de error.
Si el libro ya está prestado, no permitir el préstamo.
Si el usuario ya tiene 3 libros, no permitir el préstamo.
*/
import java.util.ArrayList;


public class Biblioteca {

    private ArrayList<Libro> libros;
    private ArrayList<Usuario> usuarios;

    public Biblioteca(){
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void añadirLibro(Libro libro){
        libros.add(libro);
    }

    public void añadirUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public Libro buscarLibro(String isbn){
        for(Libro libro: libros){
            if(libro.getIsbn().equals(isbn)){
                return libro;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(int id){
        for(Usuario usuario: usuarios){
            if(usuario.getId()== id){
                return usuario;
            }
        }
        return null;
    }

    public void prestarLibro(String isbn, int id){

        Libro libro = buscarLibro(isbn);

        if(libro == null){

        }

        if()

    }
 }