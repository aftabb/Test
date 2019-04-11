package com.example.aftbhadk.test.DInjection.Module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

// Created by aftbhadk on 04/04/19.
@Module
public class AppModule {

     Application application;

   public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application getApplication() {
        return application;
    }
}
