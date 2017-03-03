package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to move question in section.")
public class FailedToMoveQuestionWithinSectionException extends Exception {
    public FailedToMoveQuestionWithinSectionException(String msg) {
        super(msg);
    }
}
