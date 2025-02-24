// This file implements the quiz game.
// It shows a quiz image, three answer options, and tracks the score.
// Each quiz question is chosen from the shared list and is removed once used.

package com.example.quizapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private List<QuizEntry> quizEntries;
    private List<QuizEntry> remainingEntries;
    private QuizEntry currentEntry;
    private int correctCount = 0;
    private int totalCount = 0;
    private ImageView quizImage;
    private TextView scoreText;
    private TextView feedbackText;
    private Button option1, option2, option3;
    private Random random = new Random();

    // onCreate: Initializes the quiz, loads quiz entries from the shared application class,
    // sets up the UI, and starts the first question.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizEntries = QuizApplication.getInstance().getQuizEntries();
        // Create a copy of quizEntries to track remaining questions
        remainingEntries = new ArrayList<>(quizEntries);

        quizImage = findViewById(R.id.quiz_image);
        scoreText = findViewById(R.id.score_text);
        feedbackText = findViewById(R.id.feedback_text);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);

        setNewQuestion();

        // Listener for answer button clicks
        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button selected = (Button) v;
                totalCount++;
                if (selected.getText().equals(currentEntry.getName())) {
                    correctCount++;
                    feedbackText.setText("Correct!");
                } else {
                    feedbackText.setText("Wrong! Correct: " + currentEntry.getName());
                }
                updateScore();
                setNewQuestion();
            }
        };

        option1.setOnClickListener(answerListener);
        option2.setOnClickListener(answerListener);
        option3.setOnClickListener(answerListener);
    }

    // updateScore: Updates the score display based on the number of correct answers and total attempts.
    private void updateScore() {
        scoreText.setText("Score: " + correctCount + " / " + totalCount);
    }

    // setNewQuestion: Sets up a new quiz question by randomly choosing an entry from the remaining list.
    // It displays the image (using a URI if available, otherwise a resource ID) and shuffles three answer options.
    // If all questions have been used, it shows a completion message.
    private void setNewQuestion() {
        if (remainingEntries.isEmpty()) {
            feedbackText.setText("Quiz completed! Final score: " + correctCount + " / " + totalCount);
            // Optionally, you can finish the activity here with finish();
            return;
        }
        int randomIndex = random.nextInt(remainingEntries.size());
        currentEntry = remainingEntries.remove(randomIndex);

        if (currentEntry.getImageUri() != null) {
            quizImage.setImageURI(Uri.parse(currentEntry.getImageUri()));
        } else {
            quizImage.setImageResource(currentEntry.getImageResId());
        }

        // Prepare answer options
        List<String> options = new ArrayList<>();
        options.add(currentEntry.getName());
        while (options.size() < 3 && quizEntries.size() >= 3) {
            String wrongName = quizEntries.get(random.nextInt(quizEntries.size())).getName();
            if (!options.contains(wrongName)) {
                options.add(wrongName);
            }
        }
        Collections.shuffle(options);
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
    }
}
