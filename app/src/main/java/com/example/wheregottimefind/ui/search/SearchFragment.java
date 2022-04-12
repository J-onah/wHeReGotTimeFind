package com.example.wheregottimefind.ui.search;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.adapters.SearchResultAdapter;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.FullReviewData;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.databinding.FragmentSearchBinding;


import java.util.Arrays;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private final String TAG = "Search";
    SearchResultAdapter adapter;
    FullReviewData full_review_data;
    RecyclerView recyclerView;
    int ratings=0;
    int prices=0;
    String searchtext = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        full_review_data = FullReviewData.getInstance();
        View root = binding.getRoot();

        if (full_review_data.getAllReviews().size() != 0) {
            recyclerView = binding.searchResults;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SearchResultAdapter(getContext(), full_review_data.getSearchResults(ratings));
            recyclerView.setAdapter(adapter);
        }


        Button highlowsorter = binding.highlowrating;
        highlowsorter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prices=0;
                ratings=1;
                updateRecyclerView(searchtext);
            }
        });

        Button lowhighsorter = binding.lowhighrating;
        lowhighsorter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prices=0;
                ratings=-1;
                updateRecyclerView(searchtext);
            }
        });
        Button pricesorter = binding.price;
        pricesorter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prices=1;
                ratings=0;
                updateRecyclerView(searchtext);
            }
        });
        Button notpricesorter = binding.notprice;
        notpricesorter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prices=-1;
                ratings=0;
                updateRecyclerView(searchtext);
            }
        });

        SearchView searchBar = binding.searchBar;
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String s) {
                searchtext =s;
                Log.i(TAG, "Search query changed to: " + s);
                showProgressBar();
                updateRecyclerView(s);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                searchtext = s;
                Log.i(TAG, "Search submitted with query: " + s);
                showProgressBar();
                updateRecyclerView(s);
                return true;
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateRecyclerView(String s) {
        recyclerView = getActivity().findViewById(R.id.search_results);
        recyclerView.setVisibility(View.INVISIBLE);
        // Skip searching if string is empty
        if (s.isEmpty()) {

            hideProgressBar();
            full_review_data.clearAll();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SearchResultAdapter(getContext(), full_review_data.getSearchResults(ratings));
            recyclerView.setAdapter(adapter);
            return;
        }

        // Clear data to prevent old data being shown

        BackendApi.getReviewsByName(getActivity(), s, fullReviews -> {
            if (fullReviews == null) {
                hideProgressBar();
                Toast.makeText(getActivity(), R.string.cannot_con_server, Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d(TAG, "Received data!");
            if (prices ==-1){
                Arrays.sort(fullReviews, (first,second)->{return (int) (first.getReview().getPrice_per_unit() -second.getReview().getPrice_per_unit());
                });
            }
            else if (prices == 1){
                Arrays.sort(fullReviews, (first,second)->{return (int) (second.getReview().getPrice_per_unit() -first.getReview().getPrice_per_unit());
                });
            }
            recyclerView.setAdapter(null);
            full_review_data.clearAll();
            for (FullReview fullreview: fullReviews) {
                full_review_data.addFullReview(fullreview);
                // Add to FullReviewData
            }

            System.out.println(full_review_data);

            // Update recycler view
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SearchResultAdapter(getContext(), full_review_data.getSearchResults(ratings));

            recyclerView.setAdapter(adapter);
            if (searchtext!=s){
                updateRecyclerView(searchtext);
            }
            else {
                hideProgressBar();
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showProgressBar() {
        ProgressBar progressBar = getActivity().findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        ProgressBar progressBar = getActivity().findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
