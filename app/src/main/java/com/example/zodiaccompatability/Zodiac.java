package com.example.zodiaccompatability;

import java.util.Calendar;
import java.util.Date;

public class Zodiac {
    private int day;
    private int month;
    private int year;

    public Zodiac(int aDay, int aMonth, int aYear) {
        Calendar.set(aYear, aMonth, aDay);
        day = aDay;
        month = aMonth;
        year = aYear;
    }

    public String getZodiac() {
        return "";
    }
}
