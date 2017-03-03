package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Question does not exist in the database.")
public class QuestionMissingException extends RuntimeException {
    public QuestionMissingException(String errorMsg) {
    }
}
