package com.example.androidapplication.presentation.adapters;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidapplication.R;
import com.example.androidapplication.presentation.uidata.PhotoViewData;

import timber.log.Timber;

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    private TextView photographer;
    private TextView description;
    private TextView likes;
    private ImageView photo;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);

        photographer = itemView.findViewById(R.id.photographer);
        description = itemView.findViewById(R.id.description);
        likes = itemView.findViewById(R.id.likes);
        photo = itemView.findViewById(R.id.photo);
    }

    @SuppressLint("SetTextI18n")
    public void bindTo(PhotoViewData photoViewData) {
        Timber.wtf("Called bindTo()");
        photographer.setText(photoViewData.getPhotographer());
        description.setText(photoViewData.getDescription());
        likes.setText(photoViewData.getLikes() + "");
        Glide.with(photo).load(photoViewData.getPhoto()).centerCrop().into(photo);
    }
}
