package com.example.wheregottimefind.ui.myreviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wheregottimefind.databinding.FragmentMyreviewsBinding;

public class MyReviewsFragment extends Fragment {

    private FragmentMyreviewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyReviewsViewModel myReviewsViewModel =
                new ViewModelProvider(this).get(MyReviewsViewModel.class);

        binding = FragmentMyreviewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMyreviews;
        myReviewsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}