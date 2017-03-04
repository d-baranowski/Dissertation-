package uk.ac.ncl.daniel.baranowski.data.exceptions;

public class DateFormatException extends Exception {
    public DateFormatException(String msg, Exception e) {
        super(msg,e);
    }
}
