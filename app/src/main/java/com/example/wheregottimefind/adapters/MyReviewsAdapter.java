package com.example.wheregottimefind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.data.pojo.FullReview;

import java.text.DecimalFormat;

public class MyReviewsAdapter extends RecyclerView.Adapter<MyReviewsAdapter.ViewHolder>{
    private final FullReview[] mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyReviewsAdapter(Context context, FullReview[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_review_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FullReview fullReview = mData[position];
        String vendorName = fullReview.getVendor().getName();
        String itemName = fullReview.getReview().getProduct_name();
        int rating = fullReview.getReview().getRating();
        String comments = fullReview.getReview().getComments();
        holder.vendorNameTextView.setText(vendorName);
        holder.itemNameTextView.setText(itemName);
        DecimalFormat df = new DecimalFormat("0.0");
        holder.avgRatingTextView.setText(holder.itemView.getContext().getString(R.string.your_rating_formatted, df.format(rating)));
        holder.commentsTextView.setText(comments);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vendorNameTextView;
        TextView itemNameTextView;
        TextView avgRatingTextView;
        TextView commentsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            this.vendorNameTextView = itemView.findViewById(R.id.myreviews_row_vendorname);
            this.itemNameTextView = itemView.findViewById(R.id.myreviews_row_itemname);
            this.avgRatingTextView = itemView.findViewById(R.id.myreviews_row_rating);
            this.commentsTextView = itemView.findViewById(R.id.myreviews_comments);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    FullReview getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}