package com.student.rating.utils;

import org.joda.time.DateTime;

import java.util.Date;

public final class DateTimeUtils {
    private DateTimeUtils() {
        throw new IllegalStateException("Util class cannot be instantiated");
    }

    public static Date getCurrentMonthStart() {
        DateTime currentDate = new DateTime();
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        return monthStart.toDate();
    }

    public static Date getCurrentMonthEnd() {
        DateTime currentDate = new DateTime();
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
        return monthEnd.toDate();
    }

    public static Date getMonthStartByDate(Date date) {
        DateTime currentDate = new DateTime(date);
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        return monthStart.toDate();
    }

    public static Date getMonthEndByDate(Date date) {
        DateTime currentDate = new DateTime(date);
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
        return monthEnd.toDate();
    }
}
