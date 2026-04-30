package com.example.itineraryplanner.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenRouterService {
    @POST("chat/completions")
    Call<OpenAiResponse> getChatCompletion(
            @Header("Authorization") String authorization,
            @Header("HTTP-Referer") String referer, // Required by OpenRouter
            @Header("X-Title") String title,       // Required by OpenRouter
            @Body OpenAiRequest request
    );
}