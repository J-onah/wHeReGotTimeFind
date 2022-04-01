package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Vendor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {
    @GET("/reviews/productName/{query}")
    Call<FullReview[]> getReviewsByProductName(@Path("query") String productName);

    @GET("/vendors/vendorName/{query}")
    Call<Vendor[]> getVendorsByVendorName(@Path("query") String vendorName);
}
