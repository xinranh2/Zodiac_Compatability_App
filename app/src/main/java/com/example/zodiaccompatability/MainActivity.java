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
import android.widget.Toast;

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

        String myJson = inputStreamToString(this.getResources().openRawResource(R.raw.zodiac1));

        zodiac = new Gson().fromJson(myJson, Zodiac.class);
        setUpUI();
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
        System.out.println("Got to getZodiac Method with date: " + calendar.get(Calendar.MONTH)
                + " " + calendar.get(Calendar.DAY_OF_MONTH));
        for (Zodiac.aZodiac z: zodiac.zodiacList) {
            System.out.println("Checking if zodiac is: " + z.name);

            Calendar startDate = z.getStartCalendar();
            startDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

            Calendar endDate = z.getEndCalendar();
            endDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            if (calendar.after(startDate) || calendar.equals(startDate)) {
                if (calendar.before(endDate) || calendar.equals(endDate)) {
                    System.out.println("StartDate: " + startDate.get(Calendar.MONTH) + startDate.get(Calendar.DAY_OF_MONTH));
                    System.out.println("EndDate: " + endDate.get(Calendar.MONTH) + endDate.get(Calendar.DAY_OF_MONTH));
                    System.out.println("Zodiac is: " + z.name);
                    return z;
                }
            }
        }

        System.out.println("Zodiac is not anything?");
        System.out.println("Zodiac is: " + zodiac.zodiacList.get(10).name);
        return zodiac.zodiacList.get(10);
    }

    public Intent populateIntent() {
        System.out.println("Got to populateIntent");
        Zodiac.aZodiac zodiac1 = getZodiac(birthday1);
        Zodiac.aZodiac zodiac2 = getZodiac(birthday2);

        Intent intent = new Intent(MainActivity.this, ResultsPage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("zodiac1", zodiac1);
        bundle.putSerializable("zodiac2", zodiac2);
        //bundle.putString("name1", zodiac1.name);
        //bundle.putString("name2", zodiac2.name);

        intent.putExtras(bundle);
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
                month = month + 1;
                System.out.println("month: " + month);
                birthday1 = new Calendar.Builder().setCalendarType("iso8601")
                        .setDate(year, month, dayOfMonth).build();
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

                month = month + 1;
                birthday2 = new Calendar.Builder().setCalendarType("iso8601")
                        .setDate(year, month, dayOfMonth).build();
                Log.d(TAG, "onDateSet: mm/dd/yyyy" + year + "/" + month + "/" + dayOfMonth);

                String date = month + "/" + dayOfMonth + "/" + year;
                secondBirthday.setText(date);

            }
        };

        final Button calculateButton = (Button) findViewById(R.id.calculate);
        calculateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (birthday1 == null || birthday2 == null) {
                    Toast.makeText(MainActivity.this, "Must input both birthdays", Toast.LENGTH_SHORT).show();
                } else {
                    ProgressBar p = (ProgressBar)findViewById(R.id.progressBar1);
                    if(p.getVisibility() != View.VISIBLE){ // check if it is visible
                        p.setVisibility(View.VISIBLE); // if not set it to visible
                        calculateButton.setVisibility(View.GONE); // use 1 or 2 as parameters.. arg0 is the view(your button) from the onclick listener
                    }

                    startActivity(populateIntent());
                }
            }
        });

    }

    public void checkInput() {
        if (birthday1 != null && birthday2 != null) {
            findViewById(R.id.calculate).setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
        }
    }
}
