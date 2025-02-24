// This is the custom Application class that holds the shared list of quiz entries.
// It is initialized when the app starts and provides global access to the quiz data.

package com.example.quizapplication;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class QuizApplication extends Application {
    private static QuizApplication instance;
    private List<QuizEntry> quizEntries;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        quizEntries = new ArrayList<>();
        // Adding some default quizquestions
        quizEntries.add(new QuizEntry("Seal", R.drawable.cute_seal));
        quizEntries.add(new QuizEntry("Fox", R.drawable.cute_fox));
        quizEntries.add(new QuizEntry("Penguin", R.drawable.cute_penguin));
    }

    public static QuizApplication getInstance() {
        return instance;
    }

    public List<QuizEntry> getQuizEntries() {
        return quizEntries;
    }
}
