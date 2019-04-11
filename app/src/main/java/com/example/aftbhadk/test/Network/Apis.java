package com.example.aftbhadk.test.Network;

import com.example.aftbhadk.test.Models.Albums;
import com.example.aftbhadk.test.Models.Photos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Created by aftbhadk on 07/04/19.
public interface Apis {
    @GET("albums")
    Call<List<Albums>> getAllAlbums(@Query("_start") int startPage, @Query("_limit") int endPage);

    @GET("photos")
    Call<List<Photos>> getAllPhotos(@Query("albumId") int albumId, @Query("_start") int startPage, @Query("_limit") int endPage);
}
