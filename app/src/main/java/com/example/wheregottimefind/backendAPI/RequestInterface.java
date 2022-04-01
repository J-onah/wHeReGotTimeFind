package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.pojo.FullReview;
import com.example.wheregottimefind.pojo.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestInterface {
    @GET("/reviews/productName/{query}")
    Call<FullReview[]> getReviewsByProductName(@Path("query") String productName);

    @POST("/signup?newUsername={username}&newPasswordHash={passwordHash}")
    Call<User> signup(@Path("username") String username, @Path("passwordHash") String passwordHash);

    @POST("/login?username={username}&passwordHash={passwordHash}")
    Call<User> login(@Path("username") String username, @Path("passwordHash") String passwordHash);
}
