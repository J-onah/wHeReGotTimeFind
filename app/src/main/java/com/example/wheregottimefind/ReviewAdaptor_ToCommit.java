////////////////
///////////////
/////////////////
//////TO COMMIT 29-03-22


package com.example.wheregottimefind;



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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//https://www.youtube.com/watch?v=18VcnYN5_LM



public class ReviewAdaptor_ToCommit extends RecyclerView.Adapter<ReviewAdaptor_ToCommit.ReviewViewHolder> {

    ArrayList<String> users, reviews, iUnit, Product_Name;
    ArrayList<Integer> iUnitsPurchased, Rating;
    ArrayList<Double> iPrice_Per_Unit;
    //ArrayList<Integer> ProductImages;
    ArrayList<String[]> ProductImages;


    String vendor_name;

    Context context, getActivity;

    private OnReviewListener THISonReviewListener;


    public ReviewAdaptor_ToCommit(Context page, ArrayList<String> users_by_reviews, ArrayList<String> reviews_for_vendor, ArrayList<String[]> PdtImgs, ArrayList<Integer> UnitsPurchased, ArrayList<String> Unit, ArrayList<Double> Price_Per_Unit, ArrayList<String> ProductName, ArrayList<Integer> inputRatings, String vendor_name_input, OnReviewListener onReviewListener){
        this.context = page;
        this.users = users_by_reviews;
        this.reviews = reviews_for_vendor;
        this.ProductImages = PdtImgs;
        this.THISonReviewListener = onReviewListener;
        this.iUnitsPurchased = UnitsPurchased;
        this.iUnit = Unit;
        this.iPrice_Per_Unit = Price_Per_Unit;
        this.Product_Name = ProductName;
        this.Rating = inputRatings;
        this.vendor_name = vendor_name_input;
    }





    //RecyclerView calls this method whenever it needs to create a new ViewHolder.
    //The method creates and initializes the ViewHolder and its associated View,
    //but does not fill in the view's contents—the ViewHolder has not yet been bound to
    //specific data.

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_box_template, parent, false);

        return new ReviewViewHolder(view, THISonReviewListener);
    }





    //RecyclerView calls this method to associate a ViewHolder with data. The method fetches the appropriate data
    //and uses the data to fill in the view holder's layout.
    //For example, if the RecyclerView displays a list of names,
    //the method might find the appropriate name in the list and fill in the view holder's
    //TextView widget.
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        final String NIL = "NIL";

        String unitText, unitsPurchasedText, PricePerUnitText, product_nameText;
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

        }
        catch(IndexOutOfBoundsException ex){
            holder.reviewsByUsername.setText("No Review");
        }

        try{
            //https://stackoverflow.com/questions/15683032/android-convert-base64-encoded-string-into-image-view
            byte[] decodedString = Base64.decode(ProductImages.get(position)[0], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ProductImage.setImageBitmap(decodedByte);
            holder.ProductImage.setScaleX((float) 0.2);
            holder.ProductImage.setScaleY((float) 0.2);



            //holder.ProductImage.setImageResource(ProductImages.get(position)[0]);

        }
        catch(IndexOutOfBoundsException ex){
            holder.ProductImage.setImageResource(R.drawable.test);
        }

        try{
            unitText = "Unit: " + iUnit.get(position);
            holder.Unit.setText(unitText);
        }
        catch(IndexOutOfBoundsException ex){
            unitText = "Unit: " + NIL;
            holder.Unit.setText(unitText);
        }

        try{
            unitsPurchasedText = "Units Purchased: " + iUnitsPurchased.get(position);
            holder.UnitsPurchased.setText(unitsPurchasedText);
        }
        catch(IndexOutOfBoundsException ex){
            unitsPurchasedText = "Units Purchased: " + NIL;
            holder.UnitsPurchased.setText(unitsPurchasedText);
        }

        try{
            PricePerUnitText = "Price per unit: $" + iPrice_Per_Unit.get(position);
            holder.Price_Per_Unit.setText(PricePerUnitText);
        }
        catch(IndexOutOfBoundsException ex){
            PricePerUnitText = "Price per unit: " + NIL;
            holder.Price_Per_Unit.setText(PricePerUnitText);
        }


        try{
            product_nameText = "Product: " + Product_Name.get(position);
            holder.product_name.setText(product_nameText);
        }
        catch(IndexOutOfBoundsException ex){
            product_nameText = "Product: " + NIL;
            holder.product_name.setText(product_nameText);
        }

        try{
            holder.ReviewRating.setRating(Rating.get(position));
        }
        catch(IndexOutOfBoundsException ex){
            holder.ReviewRating.setRating(0);
        }


        holder.ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Picture", "Picture "+position+" clicked");
                Bundle args = new Bundle();
                args.putString("vendor_name", vendor_name);
                if (!ProductImages.isEmpty()) {
                    args.putStringArray("ProductImages", ProductImages.get(position));
                }
                Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_PhotoFragment, args);
            }
        });

    }

    public void setContext(Context context){
        getActivity = context;
    }





    //RecyclerView calls this method to get the size of the data set.
    //For example, in an address book app, this might be the total number of addresses.
    //RecyclerView uses this to determine when there are no more items that can be displayed.

    @Override
    public int getItemCount() {

        return reviews.size();

    }



    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView username, reviewsByUsername, product_name;
        ImageView ProductImage;
        TextView UnitsPurchased, Unit, Price_Per_Unit;
        RatingBar ReviewRating;


        OnReviewListener onReviewListener;

        public ReviewViewHolder(@NonNull View itemView, OnReviewListener onReviewListener) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            reviewsByUsername = itemView.findViewById(R.id.reviewsByUsername);
            ProductImage = itemView.findViewById(R.id.ProductImage);
            product_name = itemView.findViewById(R.id.product_name);

            UnitsPurchased = itemView.findViewById(R.id.UnitsPurchased);
            Unit = itemView.findViewById(R.id.Unit);
            Price_Per_Unit = itemView.findViewById(R.id.Price_Per_Unit);

            ReviewRating = itemView.findViewById(R.id.ReviewRating);

            this.onReviewListener = onReviewListener;


            //https://www.youtube.com/watch?v=69C1ljfDvl0
            itemView.setOnClickListener(this);

        }

        //https://www.youtube.com/watch?v=69C1ljfDvl0
        @Override
        public void onClick(View view) {
            onReviewListener.onReviewClick(getBindingAdapterPosition(), ReviewAdaptor_ToCommit.this);

        }
    }

    public interface OnReviewListener{
        void onReviewClick(int position, ReviewAdaptor_ToCommit reviewAdaptor);
    }


}


// TO COMMIT 29-03-22