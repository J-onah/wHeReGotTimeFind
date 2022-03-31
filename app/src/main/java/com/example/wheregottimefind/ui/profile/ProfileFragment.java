package com.example.wheregottimefind.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wheregottimefind.MainActivity;
import com.example.wheregottimefind.data.LoginRepository;
import com.example.wheregottimefind.data.model.LoggedInUser;
import com.example.wheregottimefind.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        ProfileViewModel profileViewModel =
//                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Get data from intent
        String displayName = getActivity().getIntent().getExtras().getString(MainActivity.DISPLAYNAMEEXTRA);
        String userid = getActivity().getIntent().getExtras().getString(MainActivity.USERIDEXTRA);

        // Set TextViews
        final TextView displayNameTextView = binding.profileDisplayname;
        final TextView useridTextView = binding.profileUserid;
        displayNameTextView.setText(displayName);
        useridTextView.setText(userid);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}