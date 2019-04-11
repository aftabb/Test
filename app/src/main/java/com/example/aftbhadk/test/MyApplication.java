package com.example.aftbhadk.test;

import android.app.Application;


import com.example.aftbhadk.test.DInjection.Component.ApiComponent;
import com.example.aftbhadk.test.DInjection.Component.DaggerApiComponent;
import com.example.aftbhadk.test.DInjection.Module.ApiModule;
import com.example.aftbhadk.test.DInjection.Module.AppModule;

// Created by aftbhadk on 04/04/19.
public class MyApplication extends Application {
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule("https://jsonplaceholder.typicode.com/")).
                        appModule(new AppModule(this)).
                        build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}
