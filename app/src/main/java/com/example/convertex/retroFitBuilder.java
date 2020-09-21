package com.example.convertex;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retroFitBuilder {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://v6.exchangerate-api.com/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
