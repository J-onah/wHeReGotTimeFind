package com.example.wheregottimefind.ui.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.SearchResultAdapter;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.FullReviewData;
import com.example.wheregottimefind.pojo.FullReview;
import com.example.wheregottimefind.databinding.FragmentSearchBinding;

import java.util.Arrays;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private final String TAG = "Search";
    SearchResultAdapter adapter;
    FullReviewData full_review_data;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        full_review_data = FullReviewData.getInstance();
        View root = binding.getRoot();

        if (full_review_data.getAllReviews().size() != 0) {
            recyclerView = binding.searchResults;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SearchResultAdapter(getContext(), full_review_data.getVendorsFromData());
            recyclerView.setAdapter(adapter);
            System.out.println("Current full_review_data: " + full_review_data.getAllReviews().toString());
        }


        SearchView searchBar = binding.searchBar;
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String s) {
                Log.i(TAG, "Search query changed to: " + s);
                showProgressBar();
                updateRecyclerView(s);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i(TAG, "Search submitted with query: " + s);
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

        // Skip searching if string is empty
        if (s.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            hideProgressBar();
            return;
        }

        // Clear data to prevent old data being shown
        recyclerView.setAdapter(null);
        recyclerView.setVisibility(View.VISIBLE);
        full_review_data.clearAll();

        BackendApi.getReviewsByName(s, fullReviews -> {
            if (fullReviews == null) {
                hideProgressBar();
                Toast.makeText(getActivity(), "Unable to connect to server!", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d(TAG, "Received data!");
            String fullString = "";
            Arrays.sort(fullReviews);
            for (FullReview fullreview: fullReviews) {

                Log.d(TAG, fullreview.toString());
                fullString += fullreview + "\n";

                // Add to FullReviewData
                full_review_data.addFullReview(fullreview);

                System.out.println(full_review_data.getAllReviews().toString());
            }

            // Update recycler view
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new SearchResultAdapter(getContext(), full_review_data.getVendorsFromData());
            adapter.setClickListener(new SearchResultAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // Pass data to result fragment
                    String vendor_name = ((TextView) view.findViewById(R.id.search_row_vendorname)).getText().toString();
                    Bundle args = new Bundle();
                    args.putString("vendor_name", vendor_name);
                    Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_resultFragment, args);
                }
            });
            recyclerView.setAdapter(adapter);
            hideProgressBar();
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