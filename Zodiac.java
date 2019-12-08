package com.example.zodiaccompatability;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

import java.io.IOException;

public class Zodiac {
    @SerializedName("zodiacs")
    public ArrayList<aZodiac> zodiacList; //array of all the zodiacs

    static public class aZodiac implements Serializable{
        @SerializedName("name") // each zodiacs name
        public String name;
        @SerializedName("date start")
        public String dateStart;
        @SerializedName("date end")
        public String dateEnd;
        @SerializedName("compatability score")
        public ArrayList<Score> compatScore; //array of all the scores associated with zodiac

        static public class Score implements Serializable{
            @SerializedName("name")
            public String name;
            @SerializedName("score")
            public int score;
        }

        public Calendar getStartCalendar() {

            String[] dates = dateStart.split("-");
            System.out.println(dates[0] + dates[1]);
            int month = Integer.parseInt(dates[0]);
            int dayOfMonth = Integer.parseInt(dates[1]);
            return new Calendar.Builder().setCalendarType("iso8601")
                    .setDate(1000, month, dayOfMonth).build();
        }
        public Calendar getEndCalendar() {
            String[] dates = dateEnd.split("-");
            int month = Integer.parseInt(dates[0]);
            int dayOfMonth = Integer.parseInt(dates[1]);
            return new Calendar.Builder().setCalendarType("iso8601")
                    .setDate(1000, month, dayOfMonth).build();
        }

        public ArrayList getCompatibilityScores() {
            return compatScore;
        }
    }
}
