package com.example.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button quizButton = findViewById(R.id.quiz_btn);
        Button galleryButton = findViewById(R.id.gallery_btn);

        quizButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        });

        galleryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Gallery.class);
            startActivity(intent);
        });
    }
}
