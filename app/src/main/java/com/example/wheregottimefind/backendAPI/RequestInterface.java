package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.pojo.User;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Vendor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestInterface {
    @GET("/reviews/productName/{query}")
    Call<FullReview[]> getReviewsByProductName(@Path("query") String productName);

    @GET("/vendors/vendorName/{query}")
    Call<Vendor[]> getVendorsByVendorName(@Path("query") String vendorName);

    @POST("/signup?newUsername={username}&newPasswordHash={passwordHash}")
    Call<User> signup(@Path("username") String username, @Path("passwordHash") String passwordHash);

    @POST("/login?username={username}&passwordHash={passwordHash}")
    Call<User> login(@Path("username") String username, @Path("passwordHash") String passwordHash);
}
