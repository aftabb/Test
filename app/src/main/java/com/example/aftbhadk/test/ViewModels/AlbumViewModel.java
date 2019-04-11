package com.example.aftbhadk.test.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.aftbhadk.test.Models.Albums;
import com.example.aftbhadk.test.MyApplication;
import com.example.aftbhadk.test.Network.ApiClient;
import com.example.aftbhadk.test.Network.Apis;
import com.example.aftbhadk.test.Repository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


// Created by aftbhadk on 07/04/19.
public class AlbumViewModel extends AndroidViewModel {
    MutableLiveData<List<Albums>> albumdata;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    @Inject
    Retrofit retrofit;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        ((MyApplication) getApplication()).getApiComponent().inject(this);
    }

    public LiveData<List<Albums>> getAlbumdata(int start, int limit) {
        if (albumdata == null) {
            albumdata = new MutableLiveData<>();
            loadAlbum(start, limit);
        }
        return albumdata;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void loadAlbum(int start, int limit) {
        Apis apiInterface = retrofit.create(Apis.class);
        isLoading.setValue(true);
        apiInterface.getAllAlbums(start, limit).enqueue(new Callback<List<Albums>>() {
            @Override
            public void onResponse(Call<List<Albums>> call, Response<List<Albums>> response) {
                albumdata.setValue(response.body());
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Albums>> call, Throwable t) {
                isLoading.setValue(false);
            }
        });


    }
}
