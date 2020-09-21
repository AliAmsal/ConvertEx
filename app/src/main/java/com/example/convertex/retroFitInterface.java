package com.example.convertex;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface retroFitInterface {
    @GET("v6/dd3e51f2649cf895146ad64d/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);

}
