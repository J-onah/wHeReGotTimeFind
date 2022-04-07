package com.example.wheregottimefind.backendAPI;

import com.example.wheregottimefind.data.pojo.Product;
import com.example.wheregottimefind.data.pojo.Review;
import com.example.wheregottimefind.data.pojo.User;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Vendor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("/reviews/productName/{query}")
    Call<FullReview[]> getReviewsByProductName(@Path("query") String productName,
                                               @Query("username") String username,
                                               @Query("authToken") String authToken);

    @GET("/reviews/username")
    Call<FullReview[]> getReviewsByUsernames(@Query("usernames") String usernames,
                                             @Query("username") String username,
                                             @Query("authToken") String authToken);

    @GET("/vendors/vendorName/{query}")
    Call<Vendor[]> getVendorsByVendorName(@Path("query") String vendorName,
                                          @Query("username") String username,
                                          @Query("authToken") String authToken);

    @GET("/products/productName/{query}")
    Call<Product[]> getProductsByProductName(@Path("query") String vendorName,
                                           @Query("username") String username,
                                           @Query("authToken") String authToken);
    @POST("/signup")
    Call<User> signup(@Query("newUsername") String username, @Query("newPasswordHash") String passwordHash);

    @POST("/login")
    Call<User> login(@Query("username") String username, @Query("passwordHash") String passwordHash);

    @POST("/reviews")
    Call<Review> postReview(@Query("username") String username,
                            @Query("authToken") String authToken,
                            @Query("existingUserId") Integer existingUserId,
                            @Query("existingProductId") Integer existingProductId,
                            @Query("newProductName") String newProductName,
                            @Query("existingVendorId") Integer existingVendorId,
                            @Query("newVendorName") String newVendorName,
                            @Query("newVendorLocation") String newVendorLocation,
                            @Query("newVendorPhoneNo") Long newVendorPhoneNo,
                            @Query("imagesData") String imagesDataArr,
                            @Query("existingTagIds") String existingTagIdsArr,
                            @Query("newTagNames") String newTagNamesArr,
                            @Query("rating") Integer rating,
                            @Query("unitsPurchased") Integer unitsPurchased,
                            @Query("unit") String unit,
                            @Query("pricePerUnit") Double pricePerUnit,
                            @Query("comments") String comments);

}
