package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if (startTime == null && endTime == null) {
            return true;
        }

        if (endTime == null) {
            return lt.compareTo(startTime) >= 0;
        }

        if (startTime == null) {
            return lt.compareTo(endTime) < 0;
        }

        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }
}
