// This file defines the QuizEntry class, which represents one quiz item.
// Each QuizEntry contains a name and an image, which can be either a built-in resource or an image chosen from the gallery.

package com.example.quizapplication;

public class QuizEntry {
    private String name;
    private int imageResId;  // Used for built-in images
    private String imageUri; // Used for images choosen from the gallery

    // Constructor for built-in images
    public QuizEntry(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
        this.imageUri = null;
    }

    // Constructor for gallery images
    public QuizEntry(String name, String imageUri) {
        this.name = name;
        this.imageUri = imageUri;
        this.imageResId = 0;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

