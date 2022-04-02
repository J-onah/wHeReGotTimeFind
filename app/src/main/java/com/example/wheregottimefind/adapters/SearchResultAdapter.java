package com.example.wheregottimefind.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.SearchResult;
import com.example.wheregottimefind.data.pojo.Vendor;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{
    private List<SearchResult> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public SearchResultAdapter(Context context, List<SearchResult> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_result_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchResult result = mData.get(position);
        String vendorName = result.getVendor().getName();
        String vendorLocation = result.getVendor().getLocation();
        long vendorPhone = result.getVendor().getPhone_no();
        int vendorId = result.getVendor().getId();
        String itemName = result.getProductName();
        holder.vendorNameTextView.setText(vendorName);
        holder.itemNameTextView.setText(itemName);

        holder.itemView.setOnClickListener(view -> {
            System.out.println(vendorPhone);
            Bundle args = new Bundle();
            args.putString("vendor_name_key", vendorName);
            args.putString("vendor_location_key", vendorLocation);
            args.putLong("vendor_phone_no_key", vendorPhone);
            args.putInt("vendor_id_key", vendorId);
            Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_resultFragment, args);
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vendorNameTextView;
        TextView itemNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            vendorNameTextView = itemView.findViewById(R.id.search_row_vendorname);
            itemNameTextView = itemView.findViewById(R.id.search_row_itemname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    SearchResult getItem(int id) {
        return mData.get(id);
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