package com.example.wheregottimefind.ui.myreviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.adapters.MyReviewsAdapter;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.databinding.FragmentMyreviewsBinding;

import java.util.Arrays;
import java.util.Collections;

public class MyReviewsFragment extends Fragment {

    private FragmentMyreviewsBinding binding;
    private final String TAG = "my_reviews";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyreviewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String currentUsername = sharedPref.getString(getString(R.string.username_key), "");
        BackendApi.getReviewsByUsernames(getActivity(), currentUsername, array -> {
            try {
                Collections.reverse(Arrays.asList(array));
                ProgressBar loadingBar = binding.myreviewsProgress;
                loadingBar.setVisibility(View.GONE);
                RecyclerView recyclerView= binding.myReviewsRecycler;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                MyReviewsAdapter adapter = new MyReviewsAdapter(getContext(), array);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                Log.i(TAG, "Unable to set views. Fragment probably destroyed. ");
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