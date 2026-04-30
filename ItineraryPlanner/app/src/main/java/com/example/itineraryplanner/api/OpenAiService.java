package com.example.itineraryplanner.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAiService {
    @POST("v1/chat/completions")
    Call<OpenAiResponse> getChatCompletion(
            @Header("Authorization") String authorization,
            @Body OpenAiRequest request
    );
}