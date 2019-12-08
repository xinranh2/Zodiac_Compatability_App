package com.example.zodiaccompatability;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView firstBirthday;
    private DatePickerDialog.OnDateSetListener firstBdayListener;
    private Calendar birthday1;

    private TextView secondBirthday;
    private DatePickerDialog.OnDateSetListener secondBdayListener;
    private Calendar birthday2;

    private Zodiac zodiac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://www.astrology-zodiac-signs.com/compatibility/



        setUpUI();
        final Button calculateButton = (Button) findViewById(R.id.calculate);
        calculateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ProgressBar p = (ProgressBar)findViewById(R.id.progressBar1);
                if(p.getVisibility() != View.VISIBLE){ // check if it is visible
                    p.setVisibility(View.VISIBLE); // if not set it to visible
                    calculateButton.setVisibility(View.GONE); // use 1 or 2 as parameters.. arg0 is the view(your button) from the onclick listener
                }

                startActivity(populateIntent());
            }
        });

    }
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }


    public Zodiac.aZodiac getZodiac(Calendar calendar) {
        String myJson = inputStreamToString(this.getResources().openRawResource(R.raw.zodiac1));

        zodiac = new Gson().fromJson(myJson, Zodiac.class);
        System.out.println(zodiac.zodiacList.get(0).name);

        for (Zodiac.aZodiac z: zodiac.zodiacList) {
            Calendar startDate = z.getStartCalendar();
            System.out.println(startDate.get(Calendar.YEAR));
            startDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            System.out.println(calendar.get(Calendar.YEAR));


            if (calendar.after(startDate)) {
                Calendar endDate = z.getEndCalendar();
                endDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

                if (calendar.before(endDate)) {
                    return z;
                }
            }
        }
        return null;
    }

    public Intent populateIntent() {

        Zodiac.aZodiac zodiac1 = getZodiac(birthday1);
        Zodiac.aZodiac zodiac2 = getZodiac(birthday2);
        Intent intent = new Intent(MainActivity.this, ResultsPage.class);
        intent.putExtra("name1", zodiac1.name);
        intent.putExtra("name2", zodiac2.name);

        intent.putExtra("scores1", zodiac1.getCompatibilityScores());
        intent.putExtra("scores2", zodiac2.getCompatibilityScores());
        return intent;
    }

    public void setUpUI() {
        firstBirthday = findViewById(R.id.dob1);

        firstBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        firstBdayListener,
                        year,month,day);

                dialog.show();
            }
        });
        firstBdayListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthday1 = new Calendar.Builder().setCalendarType("iso8601")
                        .setDate(year, month, dayOfMonth).build();
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy" + year + "/" + month + "/" + dayOfMonth);

                String date = month + "/" + dayOfMonth + "/" + year;
                firstBirthday.setText(date);
            }
        };

        secondBirthday = findViewById(R.id.dob2);

        secondBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        secondBdayListener,
                        year,month,day);

                dialog.show();

            }
        });
        secondBdayListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthday2 = new Calendar.Builder().setCalendarType("iso8601")
                        .setDate(year, month, dayOfMonth).build();
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy" + year + "/" + month + "/" + dayOfMonth);

                String date = month + "/" + dayOfMonth + "/" + year;
                secondBirthday.setText(date);

            }
        };

    }
}
