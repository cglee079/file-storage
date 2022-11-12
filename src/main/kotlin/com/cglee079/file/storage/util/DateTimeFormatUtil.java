package com.cglee079.file.storage.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DateTimeFormatUtil {

    public static String dateTimeToDateTimeStr(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(dateTimeFormatter);
    }

    public static String toFullDateTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return dateTime.format(dateTimeFormatter);
    }


    public static String dateTimeToBeautifulDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return "";
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US);
        return dateTime.format(dateTimeFormatter);
    }
}
