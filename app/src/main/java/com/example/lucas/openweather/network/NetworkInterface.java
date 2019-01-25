package com.example.lucas.openweather.network;

import com.example.lucas.openweather.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NetworkInterface {

        @Headers("Content-Type: application/json")
        @GET("/data/2.5/weather")
        Call<Feed> getData(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String appid, @Query("lang") String lang, @Query("units") String units);
}


