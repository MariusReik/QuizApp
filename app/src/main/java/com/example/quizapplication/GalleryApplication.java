package com.example.quizapplication;

import android.app.Application;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class GalleryApplication extends Application {
    private final List<GalleryItem> galleryCollection = new ArrayList<>();

    public List<GalleryItem> getGalleryCollection() {
        return galleryCollection;
    }

    public void addGalleryItem(Uri imageUri, String title) {
        galleryCollection.add(new GalleryItem(imageUri.toString(), title));
    }
}
