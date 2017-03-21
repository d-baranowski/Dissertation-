package uk.ac.ncl.daniel.baranowski.common;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.ac.ncl.daniel.baranowski.data.exceptions.DateFormatException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {
    private static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());

    private DateUtils() {

    }

    public static void validDateFormat(String date) throws DateFormatException {
        try {
            DATE_TIME_FORMATTER.parseLocalDate(date);
        } catch (IllegalArgumentException e) {
            String msg = "Failed to parse date " + date;
            LOGGER.log(Level.WARNING, msg , e);
            throw new DateFormatException(msg, e);
        }
    }

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy");;
}
