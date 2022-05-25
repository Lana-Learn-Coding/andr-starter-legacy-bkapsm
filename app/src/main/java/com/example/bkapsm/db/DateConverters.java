package com.example.bkapsm.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverters {
    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp);

    }

    @TypeConverter
    public static Long dateTimeToTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }
}