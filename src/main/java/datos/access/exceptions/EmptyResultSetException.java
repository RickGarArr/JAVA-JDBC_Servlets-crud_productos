package datos.access.exceptions;

public class EmptyResultSetException extends Exception {

    public EmptyResultSetException(String message) {
        super(message);
    }
    
    public EmptyResultSetException(int id) {
        super("No existe comercio con ID '" + id + "'");
    }
    
}
