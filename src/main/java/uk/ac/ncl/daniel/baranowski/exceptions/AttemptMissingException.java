package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Attempt does not exist in the database.")
public class AttemptMissingException extends DataAccessException {
    public AttemptMissingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
