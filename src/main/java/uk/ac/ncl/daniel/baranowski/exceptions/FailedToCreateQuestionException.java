package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Question could not be created.")
public class FailedToCreateQuestionException  extends NestedRuntimeException {
    public FailedToCreateQuestionException(String msg) {
        super(msg);
    }
}
