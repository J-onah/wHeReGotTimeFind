package com.example.wheregottimefind.backendAPI;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to aid in obtaining data from the backend/database
 */

public class BackendApi {
    private static final String API_ENDPOINT = "";
    private static final String TAG = "backend_api";
    private static Call<FullReviewResponse> call;

    /**
     * Asynchronously obtains reviews from database, searched by name
     *
     * @param updater an object implementing the AsyncUpdate updater, called on data received
     */

    public static void getReviewsByName(AsyncUpdate updater) {
        getData("getReviewsByName", updater);
    }

    /**
     * Asynchronously obtains reviews from database
     *
     * @param queryType string representing the type of query to obtain
     * @param updater   an object implementing the AsyncUpdate updater, called on data received
     */
    
    private static void getData(String queryType, AsyncUpdate updater) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        if (queryType == "getReviewsByName") {
            call = request.getReviewsByName();
        } else throw new IllegalArgumentException("Backend query type not found!");

        call.enqueue(new Callback<FullReviewResponse>() {
            @Override
            public void onResponse(Call<FullReviewResponse> call, Response<FullReviewResponse> response) {
                Log.d(TAG, "Response received!");
                FullReviewResponse fullReviews = response.body();
//                System.out.println(fullReviews.getFullReviews().length);
                updater.updateOnDataReceived(fullReviews.getFullReviews());
            }

            @Override
            public void onFailure(Call<FullReviewResponse> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        });
    }
}