package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to remove question from section.")
public class FailedToRemoveQuestionFromSectionException extends Exception {
    public FailedToRemoveQuestionFromSectionException(String errorMsg) {
        super(errorMsg);
    }
}
