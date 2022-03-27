package com.example.wheregottimefind.backendAPI;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("getReviewsByName")
    Call<FullReviewResponse> getReviewsByName();
}
