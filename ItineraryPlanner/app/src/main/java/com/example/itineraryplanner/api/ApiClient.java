package com.example.itineraryplanner.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit geminiRetrofit = null;
    private static Retrofit openRouterRetrofit = null;

    public static Retrofit getGeminiClient() {
        if (geminiRetrofit == null) {
            geminiRetrofit = createRetrofit("https://generativelanguage.googleapis.com/");
        }
        return geminiRetrofit;
    }

    public static Retrofit getOpenRouterClient() {
        if (openRouterRetrofit == null) {
            openRouterRetrofit = createRetrofit("https://openrouter.ai/api/v1/");
        }
        return openRouterRetrofit;
    }

    private static Retrofit createRetrofit(String baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}