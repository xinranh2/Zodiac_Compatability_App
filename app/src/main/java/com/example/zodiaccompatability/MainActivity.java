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

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView firstBirthday;
    private DatePickerDialog.OnDateSetListener firstBdayListener;
    private TextView secondBirthday;
    private DatePickerDialog.OnDateSetListener secondBdayListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button calculateButton = (Button) findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ProgressBar p = (ProgressBar)findViewById(R.id.progressBar1);
                if(p.getVisibility() != View.VISIBLE){ // check if it is visible
                    p.setVisibility(View.VISIBLE); // if not set it to visible
                    calculateButton.setVisibility(View.GONE); // use 1 or 2 as parameters.. arg0 is the view(your button) from the onclick listener
                }
                startActivity(new Intent(MainActivity.this, ResultsPage.class));
            }
        });

        //https://www.astrology-zodiac-signs.com/compatibility/
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
                Log.d(TAG, "onDateSet: mm/dd/yyyy" + year + "/" + month + "/" + dayOfMonth);

                String date = month + "/" + dayOfMonth + "/" + year;
                secondBirthday.setText(date);
            }
        };

    }
}
