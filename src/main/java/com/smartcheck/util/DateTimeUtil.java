package com.smartcheck.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtil {

    private static final String pattern = "dd-MM-yyyy hh:mm:ss a";

    /**
     * Converts a String in "dd-MM-yyyy hh:mm:ss a" format to LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateTimeStr); // parse string to java.util.Date
        return date.toInstant()                // convert to Instant
                .atZone(ZoneId.systemDefault()) // apply system default timezone
                .toLocalDateTime();             // convert to LocalDateTime
    }

    /**
     * Converts a LocalDateTime to String in "dd-MM-yyyy hh:mm:ss a" format
     */
    public static String localDateTimeToString(LocalDateTime dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        return sdf.format(date).toUpperCase();
    }

}

