package com.example.assignment7;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherapi {
    @GET("weather")
    Call<RequiredJson> getweather(@Query("q") String city, @Query("appid") String apiKey);


}
