package com.example.wheregottimefind.backendAPI;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Product;
import com.example.wheregottimefind.data.pojo.Review;
import com.example.wheregottimefind.data.pojo.Vendor;
import com.example.wheregottimefind.data.pojo.User;

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
        getData(context, "getReviewsByProductName", new Callback<FullReview[]>() {
            @Override
            public void onResponse(Call<FullReview[]> call, Response<FullReview[]> response) {
                Log.d(TAG, "Response received! " + response.code());
                FullReview[] fullReviews = response.body();
                if (response.code() == 200) {
                    updater.updateOnDataReceived(fullReviews);
                }
            }

            @Override
            public void onFailure(Call<FullReview[]> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, productName);
    }

    public static void getVendorsByVendorName(Context context, String vendorName, AsyncUpdate<Vendor[]> updater) {
        getData(context, "getVendorsByVendorName", new Callback<Vendor[]>() {
            @Override
            public void onResponse(Call<Vendor[]> call, Response<Vendor[]> response) {
                Log.d(TAG, "Response received! " + response.code());
                Vendor[] vendors = response.body();
                if (vendors != null) {
                    Log.d(TAG, vendors.toString());
                }
                if (response.code() == 200) {
                    updater.updateOnDataReceived(vendors);
                }
            }

            @Override
            public void onFailure(Call<Vendor[]> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, vendorName);
    }

    public static void getProductsByProductName(Context context, String productName, AsyncUpdate<Product[]> updater) {
        getData(context, "getProductsByProductName", new Callback<Product[]>() {
            @Override
            public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                Log.d(TAG, "Response received! " + response.code());
                Product[] products = response.body();
                if (products != null) {
                    Log.d(TAG, products.toString());
                }
                if (response.code() == 200) {
                    updater.updateOnDataReceived(products);
                }
            }

            @Override
            public void onFailure(Call<Product[]> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, productName);
    }

    public static void register(String username, String passwordHash, AsyncUpdate<User> updater) {
        getData(null, "register", new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "Response received! " + response.code());
                User user = response.body();
                if (user != null) {
                    Log.d(TAG, user.toString());
                }
                if (response.code() == 200) {
                    updater.updateOnDataReceived(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, username, passwordHash);
    }

    public static void login(String username, String passwordHash, AsyncUpdate<User> updater) {
        getData(null, "login", new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "Response received! " + response.code());
                User user = response.body();
                if (user != null) {
                    Log.d(TAG, user.toString());
                }
                if (response.code() == 200) {
                    updater.updateOnDataReceived(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.getMessage());
            }
        }, username, passwordHash);
    }

    public static void postReview(Context context, Integer existingProductId,
                                  String newProductName, Integer existingVendorId,
                                  String newVendorName, String newVendorLocation,
                                  Long newVendorPhoneNo, String imagesDataArr,
                                  String existingTagIdsArr, String newTagNamesArr,
                                  Integer rating, Integer unitsPurchased,
                                  String unit, Double pricePerUnit,
                                  String comments, AsyncUpdate<Review> updater) {

        String authToken = "";
        String username = "";
        int userid = 0;
        Log.i(TAG, "Posting review");
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
            username = sharedPref.getString(context.getString(R.string.username_key), "");
            userid = sharedPref.getInt(context.getString(R.string.userid_key), 0);
        }

        Call<Review> callNewReview = request.postReview(username, authToken, userid, existingProductId,
            newProductName, existingVendorId, newVendorName, newVendorLocation,
            newVendorPhoneNo, imagesDataArr, existingTagIdsArr, newTagNamesArr,
            rating, unitsPurchased, unit, pricePerUnit,
            comments);
        System.out.println(callNewReview.toString());
        callNewReview.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.d(TAG, "Response received! " + response.code());
                Review review = response.body();
                if (review != null) {
                    Log.d(TAG, review.toString());
                }
                updater.updateOnDataReceived(review);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d(TAG, "Failed to get response! " + t.toString());
            }
        });

    }

    /**
     * Asynchronously obtains reviews from database
     *
     * @param queryType string representing the type of query to obtain
     * @param updater   an object implementing the AsyncUpdate updater, called on data received
     */
    
    private static void getData(Context context, String queryType, Callback callback, String... queries) {
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
            username = sharedPref.getString(context.getString(R.string.username_key), "");
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

            case "getProductsByProductName":
                Call<Product[]> callProduct = request.getProductsByProductName(queries[0], username, authToken);
                callProduct.enqueue(callback);
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