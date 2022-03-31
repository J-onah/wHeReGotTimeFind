package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.pojo.FullReview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {
    @GET("/reviews/productName/{query}")
    Call<FullReview[]> getReviewsByProductName(@Path("query") String productName);
}
