package com.example.wheregottimefind.backendAPI;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Vendor;
import com.example.wheregottimefind.data.pojo.User;

import java.util.Locale;

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


    public static void getReviewsByName(Context context, String productName, AsyncUpdate<FullReview[]> updater) {
        getData(context, "getReviewsByProductName", updater, new Callback<FullReview[]>() {
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
        }, productName);
    }

    public static void getVendorsByVendorName(Context context, String vendorName, AsyncUpdate<Vendor[]> updater) {
        getData(context, "getVendorsByVendorName", updater, new Callback<Vendor[]>() {
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
        }, vendorName);
    }

    public static void register(String username, String passwordHash, AsyncUpdate<User> updater) {
        getData(null, "register", updater, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "Response received!" + response);
                User user = response.body();
                if (user != null) {
                    Log.d(TAG, user.toString());
                }
                updater.updateOnDataReceived(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, username, passwordHash);
    }

    public static void login(String username, String passwordHash, AsyncUpdate<User> updater) {
        getData(null, "login", updater, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "Response received!");
                User user = response.body();
                if (user != null) {
                    Log.d(TAG, user.toString());
                }
                updater.updateOnDataReceived(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, username, passwordHash);
    }

    /**
     * Asynchronously obtains reviews from database
     *
     * @param queryType string representing the type of query to obtain
     * @param updater   an object implementing the AsyncUpdate updater, called on data received
     */
    
    private static void getData(Context context, String queryType,
                                AsyncUpdate updater, Callback callback, String... queries) {
        String authToken = "";
        String username = "";
        Log.i(TAG, "Fetching querytype " + queryType + " with query " + queries.toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);

        // Get authToken from Shared Preferences
        if (context != null) {
            SharedPreferences sharedPref = context
                    .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            authToken = sharedPref.getString(context.getString(R.string.temp_auth_key), "");
            username = sharedPref.getString(context.getString(R.string.userid_key), "");
        }

        switch (queryType) {
            case "getReviewsByProductName":
                Call<FullReview[]> callReview = request.getReviewsByProductName(queries[0], username, authToken);
                callReview.enqueue(callback);
                break;

            case "getVendorsByVendorName":
                Call<Vendor[]> callVendor = request.getVendorsByVendorName(queries[0], username, authToken);
                callVendor.enqueue(callback);
                break;

            case "register":
                Call<User> callUser = request.signup(queries[0], queries[1]);
                callUser.enqueue(callback);
                break;

            case "login":
                Call<User> callUserLogin = request.login(queries[0], queries[1]);
                callUserLogin.enqueue(callback);
                break;


            default:
                throw new IllegalArgumentException("Backend query type not found!");
        }

    }
}