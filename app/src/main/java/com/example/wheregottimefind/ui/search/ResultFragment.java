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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_result, container, false);

        vendorNameTextView = rootView.findViewById(R.id.result_vendor_name);
        vendorLocationTextView = rootView.findViewById(R.id.VendorLocation);
        vendorPhoneTextView = rootView.findViewById(R.id.VendorPhoneNo);

        // Set vendor details
        vendorNameTextView.setText(vendorName);
        vendorLocationTextView.setText(vendorLocation);
        if (vendorPhone <= 0) {
            vendorPhoneTextView.setText("Phone Number Unknown");
        } else {
            vendorPhoneTextView.setText(String.valueOf(vendorPhone));
        }

        //TextView result_fragment_text = rootView.findViewById(R.id.result_fragment_text);
        //result_fragment_text.setText("Result fragment got vendor name: " + String.valueOf(vendor_name));


















        //////////////////// RECYCLERVIEW, To have a template for each review
        ///////////////////////////////////////////////////////////////////////////////////






        ///// TESTING PURPOSES
        ////////////////////

//        String REVIEWTEST = "This is a nice shop that that that that that that that that that that that that that that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that thatthat that that that that that that that that that that that";
//
//
//        Review testReview = new Review("User1", "nails", 5, 3, "piece", 5, new String[]{"1", "2", "3"}, new String[]{"R.drawable.chip"}, REVIEWTEST);
//        Vendor testVendor = new Vendor("POPULAR", "No location availble", 98765432, 1);

        //productImages.add(R.drawable.chip);
        //users_by_reviews.add("User 1");
        //reviews_for_vendor.add(REVIEWTEST);
        //reviews_for_vendor_TRUNCATE.add(REVIEWTEST.substring(0, 50) + " ...");

//        REVIEWTEST = "This is a BAD shop     very BAD     don't go    such nah    very no   avoid much.";
//        Review testReview2 = new Review("User2", "nails", 3, 3, "piece", 5, new String[]{"1", "2", "3"}, new String[]{"R.drawable.chip"}, REVIEWTEST);

        //reviews_for_vendor.add(REVIEWTEST);
        //reviews_for_vendor_TRUNCATE.add(REVIEWTEST.substring(0, 50) + " ...");

        //reviews_for_vendor.add("Review: This is a nice shop that ........");
        //users_by_reviews.add("User 2");
        //users_by_reviews.add("User 3");










        /*
        /////////////////// HARDCODED TEST SECTION
        this.vendorNameTextView.setText(testVendor.getName());
        vendorLocationTextView.setText(testVendor.getLocation());
        vendorPhoneTextView.setText(String.valueOf(testVendor.getPhone_no()));
        users_by_reviews.add(testReview.getUserid());
        users_by_reviews.add(testReview2.getUserid());
        productName.add(testReview.getProduct_name());
        productName.add(testReview2.getProduct_name());
        unit.add(testReview.getUnit());
        unit.add(testReview2.getUnit());
        unitsPurchased.add(testReview.getUnits_purchased());
        unitsPurchased.add(testReview2.getUnits_purchased());
        price_per_unit.add(testReview.getPrice_per_unit());
        price_per_unit.add(testReview2.getPrice_per_unit());
        reviewRating.add(testReview.getRating());
        reviewRating.add(testReview2.getRating());
        reviews_for_vendor.add(testReview.getComments());
        reviews_for_vendor_TRUNCATE.add(testReview.getComments().substring(0, 50) + " ...");
        reviews_for_vendor.add(testReview2.getComments());
        reviews_for_vendor_TRUNCATE.add(testReview2.getComments().substring(0, 50) + " ...");
        ////////////////////////
        */










        //vendorId = 1;

//        full_review_data.clearAll(); ///////////////////// WARNING: FOR TEST PURPOSES. REMOVE AFTER TEST. Due to bottom 4 code lines, which causes repeats when returning to page.

        /// TESTING FullReview objects and using them to setup a test version of FullReviewData instance
//        FullReview testFullReviewObj1 = new FullReview(testVendor, testReview);
//        FullReview testFullReviewObj2 = new FullReview(testVendor, testReview2);


//        full_review_data.addFullReview(testFullReviewObj1);
//        full_review_data.addFullReview(testFullReviewObj2);
        //////////////////






        /////////////// GENERAL CODES



        list_of_FullReview_Obj = full_review_data.getReviewsByVendorIdAndItemName(vendorId, itemName);
//        System.out.println(vendorId);
//        System.out.println(list_of_FullReview_Obj);
//        this.vendorNameTextView.setText(list_of_FullReview_Obj.get(0).getVendor().getName());
//        vendorLocationTextView.setText(list_of_FullReview_Obj.get(0).getVendor().getLocation());
//        vendorPhoneTextView.setText(String.valueOf(list_of_FullReview_Obj.get(0).getVendor().getPhone_no()));


        for(FullReview eachFullReview: list_of_FullReview_Obj) {
            users_by_reviews.add(eachFullReview.getReview().getUserid());

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
            reviewAdaptor.setContext(getActivity());  //https://stackoverflow.com/questions/45336048/why-does-my-android-adapter-not-have-the-getactivity-method

            recyclerViewReviews.setAdapter(reviewAdaptor);
            recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
            //https://stackoverflow.com/questions/26621060/display-a-recyclerview-in-fragment




        } catch (NullPointerException ex) {
            reviews.setText("\n\nNo reviews currently \n");
            Log.i("Reviews", "There is no review");
        }

        //https://www.youtube.com/watch?v=18VcnYN5_LM



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

        reviewAdaptor.notifyItemChanged(position); //https://stackoverflow.com/questions/32457406/how-to-update-refresh-specific-item-in-recyclerview




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
