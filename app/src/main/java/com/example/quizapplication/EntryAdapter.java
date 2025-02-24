// This file contains the adapter for our RecyclerView.
// It binds each QuizEntry (which contains a name and an image)
// to the layout for a single list item (entry_item.xml) and handles click events.

package com.example.quizapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<QuizEntry> quizEntries;
    private Context context;

    public EntryAdapter(Context context, List<QuizEntry> quizEntries) {
        this.context = context;
        this.quizEntries = quizEntries;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.entry_image);
            nameView = itemView.findViewById(R.id.entry_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.entry_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final QuizEntry entry = quizEntries.get(position);
        holder.nameView.setText(entry.getName());
        if (entry.getImageUri() != null) {
            holder.imageView.setImageURI(Uri.parse(entry.getImageUri()));
        } else {
            holder.imageView.setImageResource(entry.getImageResId());
        }

        // When the image is clicked, remove the entry from the list
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizEntries.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizEntries.size();
    }
}

