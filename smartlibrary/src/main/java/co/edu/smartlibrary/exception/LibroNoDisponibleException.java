package co.edu.smartlibrary.exception;

/** Libro ya prestado -> 409 Conflict. */
public class LibroNoDisponibleException extends RuntimeException {

    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
