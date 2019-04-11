package com.example.aftbhadk.test.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aftbhadk.test.Models.Albums;
import com.example.aftbhadk.test.R;
import com.example.aftbhadk.test.Views.Albums.AlbumInterface;
import com.example.aftbhadk.test.databinding.AlbumRowBinding;

import java.util.List;

// Created by aftbhadk on 07/04/19.
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

    List<Albums> albumsList;
    Context context;
    AlbumInterface albumInterface;

    public AlbumAdapter(List<Albums> albumsList, Context context, AlbumInterface albumInterface) {
        this.albumsList = albumsList;
        this.context = context;
        this.albumInterface = albumInterface;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AlbumRowBinding albumRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.album_row, viewGroup, false);

        return new AlbumHolder(albumRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder albumHolder, int i) {
        albumHolder.albumRowBinding.setAlbum(albumsList.get(i));

    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {

        private final AlbumRowBinding albumRowBinding;

        public AlbumHolder(@NonNull AlbumRowBinding itemView) {
            super(itemView.getRoot());
            this.albumRowBinding = itemView;
            albumRowBinding.mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    albumInterface.onClickAlbum(getAdapterPosition());
                }
            });
        }
    }
}
