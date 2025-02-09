package com.example.quizapplication;

public class GalleryItem {
    private final String imageUri;
    private final String title;
    private Integer imageResId;

    public GalleryItem(String imageUri, String title) {
        this.imageUri = imageUri;
        this.title = title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getTitle() {
        return title;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }
}
