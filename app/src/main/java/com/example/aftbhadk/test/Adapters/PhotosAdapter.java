package com.example.aftbhadk.test.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.aftbhadk.test.Models.Photos;
import com.example.aftbhadk.test.R;
import com.example.aftbhadk.test.Views.Photos.PhotosInterface;
import com.example.aftbhadk.test.databinding.PhotoRowBinding;

import java.util.List;

// Created by aftbhadk on 07/04/19.
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    List<Photos> photosList;
    Context context;
    PhotosInterface photosInterface;

    public PhotosAdapter(List<Photos> photosList, Context context, PhotosInterface photosInterface) {
        this.photosList = photosList;
        this.context = context;
        this.photosInterface = photosInterface;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PhotoRowBinding photoRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.photo_row, viewGroup, false);
        return new PhotoViewHolder(photoRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, int i) {
        photoViewHolder.photoRowBinding.setPhotos(photosList.get(i));
        Glide
                .with(context)
                .load(photosList.get(i).getThumbnailUrl())
                .centerCrop()
                .into(photoViewHolder.photoRowBinding.srcImage);


    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        PhotoRowBinding photoRowBinding;

        public PhotoViewHolder(@NonNull PhotoRowBinding itemView) {
            super(itemView.getRoot());
            this.photoRowBinding = itemView;
            this.photoRowBinding.mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photosInterface.OnClickPhotos(getAdapterPosition());
                }
            });
        }
    }
}
