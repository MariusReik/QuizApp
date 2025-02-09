package com.example.quizapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private final Context context;
    private final List<GalleryItem> items;

    public GalleryAdapter(Context context, List<GalleryItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = (convertView != null) ? convertView :
                LayoutInflater.from(context).inflate(R.layout.list_item_gallery, parent, false);

        ImageView imageView = view.findViewById(R.id.itemImage);
        TextView textView = view.findViewById(R.id.itemText);

        GalleryItem currentItem = items.get(position);

        if (currentItem.getImageUri() != null && !currentItem.getImageUri().isEmpty()) {
            imageView.setImageURI(Uri.parse(currentItem.getImageUri()));
        } else if (currentItem.getImageResId() != null) {
            imageView.setImageResource(currentItem.getImageResId());
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground); // Fallback
        }

        textView.setText(currentItem.getTitle());
        return view;
    }
}
