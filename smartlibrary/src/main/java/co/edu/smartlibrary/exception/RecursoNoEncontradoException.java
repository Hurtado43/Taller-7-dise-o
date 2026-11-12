package co.edu.smartlibrary.exception;

/** Estudiante o libro inexistente -> 404 Not Found. */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
