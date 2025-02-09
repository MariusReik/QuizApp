package com.example.quizapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Collections;
import java.util.List;

public class Gallery extends AppCompatActivity {
    private GalleryAdapter adapter;
    private List<GalleryItem> galleryCollection;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private boolean isSortedAscending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Access the global gallery collection
        GalleryApplication app = (GalleryApplication) getApplication();
        galleryCollection = app.getGalleryCollection();

        adapter = new GalleryAdapter(this, galleryCollection);
        ListView listView = findViewById(R.id.galleryListView);
        listView.setAdapter(adapter);

        // Register image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            promptForTitle(imageUri);
                        } else {
                            Toast.makeText(this, "Image selection failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        FloatingActionButton backFab = findViewById(R.id.back_btn);
        backFab.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        // FloatingActionButton to open image picker
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> openImagePicker());

        // Sorting button
        Button sortButton = findViewById(R.id.sort_btn);
        sortButton.setOnClickListener(view -> toggleSort());
    }

    private void openImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, 1001);
            }
        } else {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
            }
        }
    }

    private void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void promptForTitle(Uri imageUri) {
        EditText input = new EditText(this);
        input.setHint("name of animal");

        new AlertDialog.Builder(this)
                .setTitle("Add Item")
                .setMessage("Enter a name for the  image:")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = input.getText().toString();
                    if (!title.isEmpty()) {
                        addToGallery(imageUri, title);
                    } else {
                        Toast.makeText(this, "Cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addToGallery(Uri imageUri, String title) {
        GalleryApplication app = (GalleryApplication) getApplication();
        app.addGalleryItem(imageUri, title);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void toggleSort() {
        if (!isSortedAscending) {
            Collections.sort(galleryCollection, (a, b) -> b.getTitle().compareTo(a.getTitle()));
            Toast.makeText(this, "Sorted Z-A", Toast.LENGTH_SHORT).show();
        } else {
            Collections.sort(galleryCollection, (a, b) -> a.getTitle().compareTo(b.getTitle()));
            Toast.makeText(this, "Sorted A-Z", Toast.LENGTH_SHORT).show();
        }
        isSortedAscending = !isSortedAscending;
        adapter.notifyDataSetChanged();
    }
}
