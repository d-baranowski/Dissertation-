package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

/**
 * When this exception is thrown inside the controller then GlobalControllerConfiguration should pick it up
 * and server appropriate error page.
 */
@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Attempt isn't locked for marking by this user")
public class NotLockedForMarkingException extends RestClientException {
    private final int testAttemptId;
    private final String userId;

    public NotLockedForMarkingException(String msg, int testAttemptId, String userId) {
        super(msg);
        this.testAttemptId = testAttemptId;
        this.userId = userId;
    }

    public NotLockedForMarkingException(String msg, int testAttemptId, String userId, Throwable ex) {
        super(msg, ex);
        this.testAttemptId = testAttemptId;
        this.userId = userId;
    }
}
