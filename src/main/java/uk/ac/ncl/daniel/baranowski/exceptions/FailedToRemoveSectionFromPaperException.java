package uk.ac.ncl.daniel.baranowski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to remove section from paper.")
public class FailedToRemoveSectionFromPaperException extends Exception {
    public FailedToRemoveSectionFromPaperException(String errorMsg) {
        super(errorMsg);
    }
}
