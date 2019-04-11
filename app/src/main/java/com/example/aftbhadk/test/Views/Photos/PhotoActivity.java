package com.example.aftbhadk.test.Views.Photos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.example.aftbhadk.test.Adapters.PhotosAdapter;
import com.example.aftbhadk.test.Common.NetworkUtils;
import com.example.aftbhadk.test.Constants;
import com.example.aftbhadk.test.Models.Photos;
import com.example.aftbhadk.test.Views.PhotoDetails.PhotoDetailsActivity;
import com.example.aftbhadk.test.R;
import com.example.aftbhadk.test.ViewModels.PhotosViewModel;
import com.example.aftbhadk.test.databinding.ActivityPhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements PhotosInterface {

    ActivityPhotoBinding activityPhotoBinding;
    PhotosViewModel photosViewModel;
    List<Photos> photosList = new ArrayList<>();
    PhotosAdapter photosAdapter;
    GridLayoutManager gridLayoutManager;
    int albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityPhotoBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);
        gridLayoutManager = new GridLayoutManager(this, 2);
        photosAdapter = new PhotosAdapter(photosList, PhotoActivity.this, this);
        Intent intent = getIntent();
        albumId = intent.getIntExtra(Constants.ID, 1);


        activityPhotoBinding.photoList.setLayoutManager(gridLayoutManager);
        activityPhotoBinding.photoList.setAdapter(photosAdapter);
        if (NetworkUtils.isNetworkConnected(this)) {
            getPhotos();
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }


    }

    public void getPhotos() {
        photosViewModel.getAllPhotos(albumId, 0, 20).observe(PhotoActivity.this, new Observer<List<Photos>>() {
            @Override
            public void onChanged(@Nullable List<Photos> photos) {
                for (int i = 0; i < photos.size(); i++) {
                    photosList.add(photos.get(i));
                    photosAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void OnClickPhotos(int position) {

        Photos photos = photosList.get(position);
        Intent detailsIntent = new Intent(PhotoActivity.this, PhotoDetailsActivity.class);
        detailsIntent.putExtra(Constants.IMAGENAME, photos.getTitle());
        detailsIntent.putExtra(Constants.URl, photos.getUrl());
        startActivity(detailsIntent);

    }
}
