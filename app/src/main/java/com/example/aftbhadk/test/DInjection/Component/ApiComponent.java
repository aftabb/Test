package com.example.aftbhadk.test.DInjection.Component;



import android.app.Application;

import com.example.aftbhadk.test.DInjection.Module.ApiModule;
import com.example.aftbhadk.test.DInjection.Module.AppModule;
import com.example.aftbhadk.test.ViewModels.AlbumViewModel;
import com.example.aftbhadk.test.Views.Albums.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

// Created by aftbhadk on 04/04/19.

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    @Singleton
    void inject(MainActivity mainActivity);

    @Singleton
    void inject(AlbumViewModel albumViewModel);
}
