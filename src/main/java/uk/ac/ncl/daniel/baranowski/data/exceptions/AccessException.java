package uk.ac.ncl.daniel.baranowski.data.exceptions;

/**
 * This exception is thrown when database access was unsuccessful.
 * It is meant to replace the runtime exceptions threw by the jdbc to
 * enforce appropriate error handling.
 */
public class AccessException extends Exception {
    private static final long serialVersionUID = 8947778460089191645L;

    /**
     * Constructor for AccessException.
     *
     * @param msg the detail message
     */
    public AccessException(String msg) {
        super(msg);
    }

    /**
     * Constructor for AccessException.
     *
     * @param msg   the detail message
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public AccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
