package com.example.aftbhadk.test.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Created by aftbhadk on 29/03/19.
public class ApiClient {
    private static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
