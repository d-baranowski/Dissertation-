package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This section does not exists")
public class SectionDoesNotExistException extends NestedRuntimeException {
    public SectionDoesNotExistException(String msg) {
        super(msg);
    }
}
