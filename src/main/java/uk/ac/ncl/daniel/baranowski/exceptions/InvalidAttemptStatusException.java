package uk.ac.ncl.daniel.baranowski.exceptions;

import uk.ac.ncl.daniel.baranowski.common.enums.AttemptStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Attempt status doesn't match expected.")
public class InvalidAttemptStatusException extends RestClientException {
    private final AttemptStatus expected;
    private final AttemptStatus actual;

    public InvalidAttemptStatusException(AttemptStatus expected, AttemptStatus actual, String msg) {
        super(msg);
        this.actual = actual;
        this.expected = expected;
    }

    public AttemptStatus getExpected() {
        return expected;
    }

    public AttemptStatus getActual() {
        return actual;
    }
}
