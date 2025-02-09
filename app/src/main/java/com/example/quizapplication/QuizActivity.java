package com.example.quizapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    private ImageView quizImageView;
    private TextView questionText;
    private Button nextButton;

    private int currentQuestionIndex = 0;

    private int[] imageResources = {
            R.drawable.cute_seal,
            R.drawable.cute_fox,
            R.drawable.cute_penguin
    };

    private String[] questions = {
            "What animal is this?",
            "Can you name this animal?",
            "Which animal is shown here?"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizImageView = findViewById(R.id.quizImageView);
        questionText = findViewById(R.id.questionText);
        nextButton = findViewById(R.id.nextButton);

        updateQuestion();

        nextButton.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % imageResources.length;
            updateQuestion();
        });
    }

    private void updateQuestion() {
        quizImageView.setImageResource(imageResources[currentQuestionIndex]);
        questionText.setText(questions[currentQuestionIndex]);
    }
}
