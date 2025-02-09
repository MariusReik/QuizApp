    package com.example.quizapplication;

    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import androidx.appcompat.app.AppCompatActivity;
    import android.widget.Toast;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.List;


    public class QuizActivity extends AppCompatActivity {
        private ImageView quizImage;
        private TextView questionText;
        private Button answer1, answer2, answer3, nextButton;

        private String correctAnswer;
        private int currentQuestionIndex = 0;

        private final QuizQuestion[] questions = {
                new QuizQuestion(R.drawable.cute_seal, "What animal is this?", "Seal", "Otter", "Walrus"),
                new QuizQuestion(R.drawable.cute_fox, "What animal is this?", "Fox", "Wolf", "Coyote"),
                new QuizQuestion(R.drawable.cute_penguin, "What animal is this?", "Penguin", "Puffin", "Seagull"),
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);

            quizImage = findViewById(R.id.quiz_image);
            questionText = findViewById(R.id.question_text);
            answer1 = findViewById(R.id.answer1_btn);
            answer2 = findViewById(R.id.answer2_btn);
            answer3 = findViewById(R.id.answer3_btn);
            nextButton = findViewById(R.id.next_btn);

            loadQuestion();

            answer1.setOnClickListener(view -> checkAnswer(answer1.getText().toString()));
            answer2.setOnClickListener(view -> checkAnswer(answer2.getText().toString()));
            answer3.setOnClickListener(view -> checkAnswer(answer3.getText().toString()));

            nextButton.setOnClickListener(view -> {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                } else {
                    Toast.makeText(this, "Quiz Finished!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

        private void loadQuestion() {
            QuizQuestion q = questions[currentQuestionIndex];
            quizImage.setImageResource(q.imageRes);
            questionText.setText(q.question);
            correctAnswer = q.correctAnswer;

            List<String> options = Arrays.asList(q.correctAnswer, q.wrongAnswer1, q.wrongAnswer2);
            Collections.shuffle(options);

            answer1.setText(options.get(0));
            answer2.setText(options.get(1));
            answer3.setText(options.get(2));
        }

        private void checkAnswer(String selectedAnswer) {
            if (selectedAnswer.equals(correctAnswer)) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong! The correct answer is " + correctAnswer, Toast.LENGTH_SHORT).show();
            }
        }

        static class QuizQuestion {
            int imageRes;
            String question, correctAnswer, wrongAnswer1, wrongAnswer2;

            public QuizQuestion(int imageRes, String question, String correctAnswer, String wrongAnswer1, String wrongAnswer2) {
                this.imageRes = imageRes;
                this.question = question;
                this.correctAnswer = correctAnswer;
                this.wrongAnswer1 = wrongAnswer1;
                this.wrongAnswer2 = wrongAnswer2;
            }
        }
    }
