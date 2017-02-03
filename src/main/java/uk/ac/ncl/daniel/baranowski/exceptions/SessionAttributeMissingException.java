package uk.ac.ncl.daniel.baranowski.exceptions;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
public class SessionAttributeMissingException extends Exception {
}
