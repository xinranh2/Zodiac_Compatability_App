package com.example.zodiaccompatability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        Intent i = getIntent();

        Bundle bundle = i.getExtras();
        Zodiac.aZodiac first = (Zodiac.aZodiac) bundle.getSerializable("zodiac1");
        Zodiac.aZodiac second = (Zodiac.aZodiac) bundle.getSerializable("zodiac2");
        int compatScore = getCompatability(first, second);

        TextView show = findViewById(R.id.results);
        show.setText(compatScore);


        Button againButton = (Button) findViewById(R.id.again);

        againButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(ResultsPage.this, MainActivity.class));
            }
        });

    }

    public int getCompatability(Zodiac.aZodiac a, Zodiac.aZodiac b) {
        for (Zodiac.aZodiac.Score i : a.compatScore) {
            if (i.name.equals(b.name)) {
                return i.score;
            }
        }
        return 0;
    }
}
