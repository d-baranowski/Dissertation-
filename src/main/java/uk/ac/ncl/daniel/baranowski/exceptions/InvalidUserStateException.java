package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "User session doesn't have necessary attribute.")
public class InvalidUserStateException extends NestedRuntimeException {
    public InvalidUserStateException(String msg) {
        super(msg);
    }
}
