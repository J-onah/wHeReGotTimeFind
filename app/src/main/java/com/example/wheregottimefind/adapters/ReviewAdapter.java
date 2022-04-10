
package com.example.wheregottimefind.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final static String TAG = "review_adapter";
    ArrayList<String> users, reviews, iUnit, product_name;
    ArrayList<Integer> iUnitsPurchased, rating;
    ArrayList<Double> iPrice_Per_Unit;
    ArrayList<String[]> productImages;

    String vendor_name;

    Context context, getActivity;

    private OnReviewListener this_onReviewListener;

    public ReviewAdapter(Context page, ArrayList<String> users_by_reviews, ArrayList<String> reviews_for_vendor, ArrayList<String[]> PdtImgs, ArrayList<Integer> UnitsPurchased, ArrayList<String> Unit, ArrayList<Double> Price_Per_Unit, ArrayList<String> ProductName, ArrayList<Integer> inputRatings, String vendor_name_input, OnReviewListener onReviewListener){
        this.context = page;
        this.users = users_by_reviews;
        this.reviews = reviews_for_vendor;
        this.productImages = PdtImgs;
        this.this_onReviewListener = onReviewListener;
        this.iUnitsPurchased = UnitsPurchased;
        this.iUnit = Unit;
        this.iPrice_Per_Unit = Price_Per_Unit;
        this.product_name = ProductName;
        this.rating = inputRatings;
        this.vendor_name = vendor_name_input;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_box_template, parent, false);

        return new ReviewViewHolder(view, this_onReviewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        final String NIL = "NIL";

        String unitText, unitsPurchasedText, pricePerUnitText, product_nameText;
        String reviewsByUsernameText;

        try{
            holder.username.setText(users.get(position));
        }
        catch(IndexOutOfBoundsException ex){
            holder.username.setText("Anonymous");
        }

        try{
            reviewsByUsernameText = "Review: " + reviews.get(position);

            holder.reviewsByUsername.setText(reviewsByUsernameText);
            
            if (reviews.get(position).substring(reviews.get(position).length() - 3).equals("...")){
                holder.see_more_see_less.setText("Tap to See More");
            }

            else if (reviews.get(position).length() > 54){
                holder.see_more_see_less.setText("Tap to See Less");
            }

            else{
                holder.see_more_see_less.setText("");
            }

        }
        catch(IndexOutOfBoundsException ex){
            holder.reviewsByUsername.setText("No Review");
        }

        try{
            byte[] decodedString = Base64.decode(productImages.get(position)[0], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.productImage.setImageBitmap(decodedByte);
        }
        catch(IndexOutOfBoundsException ex){
            holder.productImage.setImageResource(R.drawable.blank_extreme_small);
        }
        catch (Exception e) {
            Log.e(TAG, "Unable to handle images" + e);
        }

        try{
            unitText = "Unit: " + iUnit.get(position);
            holder.unit.setText(unitText);
        }
        catch(IndexOutOfBoundsException ex){
            unitText = "Unit: " + NIL;
            holder.unit.setText(unitText);
        }

        try{
            unitsPurchasedText = "Units Purchased: " + iUnitsPurchased.get(position);
            holder.unitsPurchased.setText(unitsPurchasedText);
        }
        catch(IndexOutOfBoundsException ex){
            unitsPurchasedText = "Units Purchased: " + NIL;
            holder.unitsPurchased.setText(unitsPurchasedText);
        }

        try{
            pricePerUnitText = "Price per unit: $" + iPrice_Per_Unit.get(position);
            holder.price_per_unit.setText(pricePerUnitText);
        }
        catch(IndexOutOfBoundsException ex){
            pricePerUnitText = "Price per unit: " + NIL;
            holder.price_per_unit.setText(pricePerUnitText);
        }


        try{
            product_nameText = "Product: " + product_name.get(position);
            holder.product_name.setText(product_nameText);
        }
        catch(IndexOutOfBoundsException ex){
            product_nameText = "Product: " + NIL;
            holder.product_name.setText(product_nameText);
        }

        try{
            holder.reviewRating.setRating(rating.get(position));
        }
        catch(IndexOutOfBoundsException ex){
            holder.reviewRating.setRating(0);
        }


        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Picture", "Picture "+position+" clicked");
                Bundle args = new Bundle();
                args.putString("vendor_name", vendor_name);
                if (!productImages.isEmpty()) {
                    args.putStringArray("productImages", productImages.get(position));
                }
                Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_PhotoFragment, args);

            }
        });

    }

    public void setContext(Context context){
        getActivity = context;
    }

    @Override
    public int getItemCount() {

        return reviews.size();

    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView username, reviewsByUsername, product_name;
        ImageView productImage;
        TextView unitsPurchased, unit, price_per_unit;
        RatingBar reviewRating;
        
        TextView see_more_see_less;

        OnReviewListener onReviewListener;

        public ReviewViewHolder(@NonNull View itemView, OnReviewListener onReviewListener) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            reviewsByUsername = itemView.findViewById(R.id.reviewsByUsername);
            productImage = itemView.findViewById(R.id.ProductImage);
            product_name = itemView.findViewById(R.id.product_name);

            unitsPurchased = itemView.findViewById(R.id.UnitsPurchased);
            unit = itemView.findViewById(R.id.Unit);
            price_per_unit = itemView.findViewById(R.id.Price_Per_Unit);

            reviewRating = itemView.findViewById(R.id.ReviewRating);

            this.onReviewListener = onReviewListener;
            
            see_more_see_less = itemView.findViewById(R.id.see_more_see_less);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onReviewListener.onReviewClick(getBindingAdapterPosition(), ReviewAdapter.this);

        }
    }

    public interface OnReviewListener{
        void onReviewClick(int position, ReviewAdapter reviewAdaptor);
    }


}


