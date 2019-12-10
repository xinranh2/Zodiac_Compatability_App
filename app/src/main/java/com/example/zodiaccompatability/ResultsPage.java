package com.example.zodiaccompatability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

public class ResultsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        setUpUi();
    }

    public void confetti(TextView view) {
        new ParticleSystem(this, 100, R.drawable.animated_confetti, 1500)
                .setScaleRange(0.7f, 1.3f)
                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setFadeOut(200, new AccelerateInterpolator())
                .oneShot(view, 100);
    }

    public int getCompatability(Zodiac.aZodiac a, Zodiac.aZodiac b) {
        for (Zodiac.aZodiac.Score i : a.compatScore) {
            if (i.name.equals(b.name)) {
                return i.score;
            }
        }
        return 0;
    }

    public void setUpUi() {
        Intent i = getIntent();

        Bundle bundle = i.getExtras();
        Zodiac.aZodiac first = (Zodiac.aZodiac) bundle.getSerializable("zodiac1");
        Zodiac.aZodiac second = (Zodiac.aZodiac) bundle.getSerializable("zodiac2");
        System.out.println(first.name + " " + second.name);
        final int compatScore = getCompatability(first, second);

        final TextView show = findViewById(R.id.results);
        show.setText(String.valueOf(compatScore) + "%");

        ViewTreeObserver viewTreeObserver = show.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    show.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (compatScore >= 90) {
                        confetti(show);
                    }
                }
            });
        }

        Button againButton = (Button) findViewById(R.id.again);

        againButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(ResultsPage.this, MainActivity.class));
            }
        });

        TextView result1 = findViewById(R.id.result1);
        TextView result2 = findViewById(R.id.result2);

        result1.setText(first.name);
        result2.setText(second.name);
    }
}
