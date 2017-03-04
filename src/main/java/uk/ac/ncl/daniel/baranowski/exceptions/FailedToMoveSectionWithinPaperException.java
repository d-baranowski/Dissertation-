package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to move section in paper.")
public class FailedToMoveSectionWithinPaperException extends Exception {
    public FailedToMoveSectionWithinPaperException(String msg) {
        super(msg);
    }
}
