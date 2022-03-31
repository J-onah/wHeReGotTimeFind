package com.example.wheregottimefind.backendAPI;

import android.util.Log;

import com.example.wheregottimefind.pojo.FullReview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to aid in obtaining data from the backend/database
 */

public class BackendApi {
    private static final String API_ENDPOINT = "https://safe-coast-45446.herokuapp.com/";
    private static final String TAG = "backend_api";
    private static Call<FullReview[]> call;

    /**
     * Asynchronously obtains reviews from database, searched by name
     *
     * @param updater an object implementing the AsyncUpdate updater, called on data received
     */


    public static void getReviewsByName(String productName, AsyncUpdate updater) {
        getData(productName, "getReviewsByProductName", updater);
    }

    /**
     * Asynchronously obtains reviews from database
     *
     * @param queryType string representing the type of query to obtain
     * @param updater   an object implementing the AsyncUpdate updater, called on data received
     */
    
    private static void getData(String productName, String queryType, AsyncUpdate updater) {
        Log.i(TAG, "Fetching querytype " + queryType + " with query " + productName);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        if (queryType == "getReviewsByProductName") {
            call = request.getReviewsByProductName(productName);
        } else throw new IllegalArgumentException("Backend query type not found!");

        call.enqueue(new Callback<FullReview[]>() {
            @Override
            public void onResponse(Call<FullReview[]> call, Response<FullReview[]> response) {
                Log.d(TAG, "Response received!");
                FullReview[] fullReviews = response.body();
//                System.out.println(fullReviews.getFullReviews().length);
                updater.updateOnDataReceived(fullReviews);
            }

            @Override
            public void onFailure(Call<FullReview[]> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        });
    }
}