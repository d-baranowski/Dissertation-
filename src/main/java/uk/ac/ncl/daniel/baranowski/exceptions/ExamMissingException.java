package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Exam could not be retrieved from the database.")
public class ExamMissingException extends DataAccessException {
    public ExamMissingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
