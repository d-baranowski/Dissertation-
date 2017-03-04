package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Exam could not be created.")
public class FailedToCreateExamException extends DataAccessException {
    public FailedToCreateExamException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
