package com.example.wheregottimefind.backendAPI;

import android.util.Log;

import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Vendor;

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

    /**
     * Asynchronously obtains reviews from database, searched by name
     *
     * @param updater an object implementing the AsyncUpdate updater, called on data received
     */


    public static void getReviewsByName(String productName, AsyncUpdate<FullReview> updater) {
        getData(productName, "getReviewsByProductName", updater, new Callback<FullReview[]>() {
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

    public static void getVendorsByVendorName(String vendorName, AsyncUpdate<Vendor> updater) {
        getData(vendorName, "getVendorsByVendorName", updater, new Callback<Vendor[]>() {
            @Override
            public void onResponse(Call<Vendor[]> call, Response<Vendor[]> response) {
                Log.d(TAG, "Response received!");
                Vendor[] vendors = response.body();
                updater.updateOnDataReceived(vendors);
            }

            @Override
            public void onFailure(Call<Vendor[]> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        });
    }

    /**
     * Asynchronously obtains reviews from database
     *
     * @param queryType string representing the type of query to obtain
     * @param updater   an object implementing the AsyncUpdate updater, called on data received
     */
    
    private static void getData(String query, String queryType,
                                AsyncUpdate updater, Callback callback) {
        Log.i(TAG, "Fetching querytype " + queryType + " with query " + query);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);

        switch (queryType) {
            case "getReviewsByProductName":
                Call<FullReview[]> callReview = request.getReviewsByProductName(query);
                callReview.enqueue(callback);
                break;

            case "getVendorsByVendorName":
                Call<Vendor[]> callVendor = request.getVendorsByVendorName(query);
                callVendor.enqueue(callback);
                break;

            default:
                throw new IllegalArgumentException("Backend query type not found!");
        }

    }
}