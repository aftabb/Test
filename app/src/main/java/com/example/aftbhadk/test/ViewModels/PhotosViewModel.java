package com.example.aftbhadk.test.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.aftbhadk.test.Models.Albums;
import com.example.aftbhadk.test.Models.Photos;
import com.example.aftbhadk.test.Network.ApiClient;
import com.example.aftbhadk.test.Network.Apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Created by aftbhadk on 07/04/19.
public class PhotosViewModel extends AndroidViewModel {
    MutableLiveData<List<Photos>> photodata;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PhotosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Photos>> getAllPhotos(int id, int start, int limit) {
        if (photodata == null) {
            photodata = new MutableLiveData<>();
            loadAlbum(id, start, limit);
        }
        return photodata;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void loadAlbum(int id, int start, int limit) {
        Apis apiInterface = ApiClient.getClient().create(Apis.class);
        isLoading.setValue(true);
        apiInterface.getAllPhotos(id, start, limit).enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
                photodata.setValue(response.body());
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }
}
