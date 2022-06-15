package com.example.podcastplayer.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PodcastApi {
    @POST("users/login")
    Call<LoginResponse> postLogin(@Body LoginCommand loginCommand);
}
