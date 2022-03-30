package com.example.wheregottimefind.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.pojo.FullReview;
import com.example.wheregottimefind.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private final String TAG = "Search";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel homeViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSearch;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button change_fragment = binding.changeSearchFragment;
        change_fragment.setOnClickListener(view -> {
            int id = 8080;
            Bundle args = new Bundle();
            args.putInt("id", id);
            Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_resultFragment, args);
        });


        SearchView searchBar = binding.searchBar;
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String s) {
                Log.i(TAG, "Search query changed to: " + s);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i(TAG, "Search submitted with query: " + s);
                BackendApi.getReviewsByName(fullReviews -> {
                    Log.d(TAG, "Received data!");
                    String fullString = "";
                    for (FullReview fullreview: fullReviews) {
                        Log.d(TAG, fullreview.toString());
                        fullString += fullreview + "\n";
                    }

                    // Update view
                    textView.setText(fullString);
                });
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
}