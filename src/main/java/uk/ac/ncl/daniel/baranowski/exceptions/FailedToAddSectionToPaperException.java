package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to add paper to paper.")
public class FailedToAddSectionToPaperException extends Exception {
    public FailedToAddSectionToPaperException(String msg) {
        super(msg);
    }
}
