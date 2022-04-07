package com.example.wheregottimefind.ui.myreviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.adapters.MyReviewsAdapter;
import com.example.wheregottimefind.adapters.SearchResultAdapter;
import com.example.wheregottimefind.backendAPI.AsyncUpdate;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.pojo.FullReview;
import com.example.wheregottimefind.data.pojo.Review;
import com.example.wheregottimefind.data.pojo.Vendor;
import com.example.wheregottimefind.databinding.FragmentMyreviewsBinding;

public class MyReviewsFragment extends Fragment {

    private FragmentMyreviewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyreviewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String currentUsername = sharedPref.getString(getString(R.string.username_key), "");
        BackendApi.getReviewsByUsernames(getActivity(), currentUsername, array -> {
            ProgressBar loadingBar = binding.myreviewsProgress;
            loadingBar.setVisibility(View.GONE);
            RecyclerView recyclerView= binding.myReviewsRecycler;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            MyReviewsAdapter adapter = new MyReviewsAdapter(getContext(), array);
            recyclerView.setAdapter(adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}