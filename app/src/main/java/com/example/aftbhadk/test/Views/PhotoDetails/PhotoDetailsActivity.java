package com.example.aftbhadk.test.Views.PhotoDetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.aftbhadk.test.Constants;
import com.example.aftbhadk.test.R;
import com.example.aftbhadk.test.databinding.ActivityPhotoDetailsBinding;

public class PhotoDetailsActivity extends AppCompatActivity {

    ActivityPhotoDetailsBinding activityPhotoDetailsBinding;
    String imageName;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        imageName = intent.getStringExtra(Constants.IMAGENAME);
        imageUrl = intent.getStringExtra(Constants.URl);
        activityPhotoDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo_details);
        activityPhotoDetailsBinding.imageName.setText(imageName);

        Glide
                .with(this)
                .load(imageUrl)
                .centerCrop()
                .into(activityPhotoDetailsBinding.mainImage);


    }
}
