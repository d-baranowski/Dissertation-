package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Attempt status doesn't match expected.")
public class InvalidAttemptStatusException extends RestClientException {
    private final ExamStatus expected;
    private final ExamStatus actual;

    public InvalidAttemptStatusException(ExamStatus expected, ExamStatus actual, String msg) {
        super(msg);
        this.actual = actual;
        this.expected = expected;
    }

    public ExamStatus getExpected() {
        return expected;
    }

    public ExamStatus getActual() {
        return actual;
    }
}
