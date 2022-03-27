package com.example.wheregottimefind.backendAPI;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    // ****** FOR TESTING ONLY ***
    // TODO: Update to actual database URL
    @GET("/iangohy/7e2defda2109a89842db7e051df0f8fa/raw/bab93173383af1255a298827e60dcb07d6cd58aa/mockData.json")
    // ****** END FOR TESTING ONLY ***
//    @GET("getReviewsByName")
    Call<FullReviewResponse> getReviewsByName();
}
