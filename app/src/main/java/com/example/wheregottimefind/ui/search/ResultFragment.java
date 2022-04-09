package com.example.wheregottimefind.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wheregottimefind.R;

import com.example.wheregottimefind.adapters.ReviewAdapter;
import com.example.wheregottimefind.data.FullReviewData;
import com.example.wheregottimefind.data.pojo.FullReview;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements ReviewAdapter.OnReviewListener{
    final static String TAG = "result_fragment";
    String vendorName;
    String vendorLocation;
    long vendorPhone;
    int vendorId;
    String itemName;

    ArrayList<String> reviews_for_vendor = new ArrayList<>();
    ArrayList<String> reviews_for_vendor_TRUNCATE = new ArrayList<>();
    RecyclerView recyclerViewReviews;
    ArrayList<String> users_by_reviews = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String[]> productImages = new ArrayList<String[]>();
    ArrayList<Integer> unitsPurchased = new ArrayList<Integer>();
    ArrayList<String> unit = new ArrayList<String>();
    ArrayList<Double> price_per_unit = new ArrayList<Double>();
    ArrayList<Integer> reviewRating = new ArrayList<Integer>();

    TextView vendorNameTextView, vendorLocationTextView, vendorPhoneTextView;
    TextView reviews;

    FullReviewData full_review_data = FullReviewData.getInstance();
    List<FullReview> list_of_FullReview_Obj;

    public ResultFragment() {

    }

 
    public static ResultFragment newInstance(int id) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            vendorName = getArguments().getString("vendor_name_key");
            vendorLocation = getArguments().getString("vendor_location_key");
            vendorPhone = getArguments().getLong("vendor_phone_no_key");
            vendorId = getArguments().getInt("vendor_id_key");
            itemName = getArguments().getString("item_name_key");
        } else {
            vendorName = "Vendor Name";
            vendorLocation = "No address available";
            vendorPhone = -1;
            vendorId = -1;
            Log.i(TAG, "No arguments received");
        }

        View rootView =  inflater.inflate(R.layout.fragment_result, container, false);

        vendorNameTextView = rootView.findViewById(R.id.result_vendor_name);
        vendorLocationTextView = rootView.findViewById(R.id.VendorLocation);
        vendorPhoneTextView = rootView.findViewById(R.id.VendorPhoneNo);

        vendorNameTextView.setText(vendorName);
        vendorLocationTextView.setText(vendorLocation);
        if (vendorPhone <= 0) {
            vendorPhoneTextView.setText("Phone Number Unknown");
        } else {
            vendorPhoneTextView.setText(String.valueOf(vendorPhone));
        }

        list_of_FullReview_Obj = full_review_data.getReviewsByVendorIdAndItemName(vendorId, itemName);

        for(FullReview eachFullReview: list_of_FullReview_Obj) {
            users_by_reviews.add(eachFullReview.getReview().getUsername());

            productName.add(eachFullReview.getReview().getProduct_name());

            unit.add(eachFullReview.getReview().getUnit());

            unitsPurchased.add(eachFullReview.getReview().getUnits_purchased());


            price_per_unit.add(eachFullReview.getReview().getPrice_per_unit());

            reviewRating.add(eachFullReview.getReview().getRating());

            productImages.add(eachFullReview.getReview().getImages());

            reviews_for_vendor.add(eachFullReview.getReview().getComments());
            if (eachFullReview.getReview().getComments().length() > 50) {
                reviews_for_vendor_TRUNCATE.add(eachFullReview.getReview().getComments().substring(0, 50) + " ...");
            } else {
                reviews_for_vendor_TRUNCATE.add(eachFullReview.getReview().getComments());
            }

        }

        reviews = (TextView) rootView.findViewById(R.id.Review);
        recyclerViewReviews = (RecyclerView) rootView.findViewById(R.id.recyclerViewReviews);
        try {

            reviews.setText(" ");

            if (reviews_for_vendor.isEmpty()) throw new NullPointerException();

            ReviewAdapter reviewAdaptor = new ReviewAdapter(getActivity(), users_by_reviews, reviews_for_vendor_TRUNCATE, productImages, unitsPurchased, unit, price_per_unit, productName, reviewRating, vendorName, this);
            reviewAdaptor.setContext(getActivity());  

            recyclerViewReviews.setAdapter(reviewAdaptor);
            recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (NullPointerException ex) {
            reviews.setText("\n\nNo reviews currently \n");
            Log.i("Reviews", "There is no review");
        }

        return rootView;

    }


    @Override
    public void onReviewClick(int position, ReviewAdapter reviewAdaptor) {
        Log.i("ResultFragment: ", "Review Clicked");
        Log.i("ResultFragment: ", Integer.toString(position));

        String temp = "";
        temp = reviews_for_vendor_TRUNCATE.get(position);
        reviews_for_vendor_TRUNCATE.set(position, reviews_for_vendor.get(position));
        reviews_for_vendor.set(position, temp);
        Log.i("Review Tapped:", "Tapped");

        reviewAdaptor.notifyItemChanged(position); 

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reviews_for_vendor = new ArrayList<>();
        reviews_for_vendor_TRUNCATE = new ArrayList<>();

        users_by_reviews = new ArrayList<>();
        productName = new ArrayList<>();

        productImages = new ArrayList<String[]>();
        unitsPurchased = new ArrayList<Integer>();
        unit = new ArrayList<String>();
        price_per_unit = new ArrayList<Double>();
        reviewRating = new ArrayList<Integer>();


        list_of_FullReview_Obj = null;
    }


}
