// This file shows the gallery screen where all quiz items are listed.
// Users can add new items by selecting an image from the gallery and entering a name,
// and they can also sort the list alphabetically (A-Z and Z-A).

package com.example.quizapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 100;
    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private List<QuizEntry> quizEntries;

    // onCreate: Sets up the gallery screen with a RecyclerView and buttons for adding and sorting items.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        quizEntries = QuizApplication.getInstance().getQuizEntries();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EntryAdapter(this, quizEntries);
        recyclerView.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btn_add);
        Button btnSortAZ = findViewById(R.id.btn_sort_az);
        Button btnSortZA = findViewById(R.id.btn_sort_za);

        // Start an intent to pick an image from the gallery when "Add" is clicked
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });

        // Sort the entries alphabetically A-Z
        btnSortAZ.setOnClickListener(view -> {
            Collections.sort(quizEntries, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            adapter.notifyDataSetChanged();
        });

        // Sort the entries alphabetically Z-A
        btnSortZA.setOnClickListener(view -> {
            Collections.sort(quizEntries, (o1, o2) -> o2.getName().compareTo(o1.getName()));
            adapter.notifyDataSetChanged();
        });
    }

    // onActivityResult: Handles the result from the image picker.
    // It shows a dialog for the user to enter a name for the selected image,
    // then adds a new QuizEntry to the list.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            final Uri selectedImage = data.getData();

            // Create an EditText for user input
            final EditText input = new EditText(this);
            input.setHint("Skriv inn bilde-navn");

            // Build and show a dialog to get the image name from the user
            new AlertDialog.Builder(this)
                    .setTitle("Nytt bilde")
                    .setMessage("Skriv inn navnet for bildet:")
                    .setView(input)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String imageName = input.getText().toString().trim();
                            if (imageName.isEmpty()) {
                                imageName = "Unnamed";
                            }
                            // Add a new quiz entry with the selected image and entered name
                            quizEntries.add(new QuizEntry(imageName, selectedImage.toString()));
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
    }
}

