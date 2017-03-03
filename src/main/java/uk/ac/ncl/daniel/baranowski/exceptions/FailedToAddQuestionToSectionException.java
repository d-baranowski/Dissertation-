package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to add question to section.")
public class FailedToAddQuestionToSectionException extends Exception {
    public FailedToAddQuestionToSectionException(String msg) {
        super(msg);
    }
}
