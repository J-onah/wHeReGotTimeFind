package com.example.wheregottimefind.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;

import com.example.wheregottimefind.adapters.ReviewImagesAdaptor;


public class PhotoFragment extends Fragment{


    RecyclerView recyclerViewPhotos;

    String[] productImages;


    final static String TAG = "photo_fragment";
    String vendor_name;

    public PhotoFragment() {
    }


    public static com.example.wheregottimefind.ui.search.PhotoFragment newInstance(String param1, String param2) {
        com.example.wheregottimefind.ui.search.PhotoFragment fragment = new com.example.wheregottimefind.ui.search.PhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            vendor_name = getArguments().getString("vendor_name");
            productImages = getArguments().getStringArray("productImages");   
            Log.i(TAG, String.valueOf(vendor_name));
        } else {
            Log.i(TAG, "No arguments received");
        }

        View rootView =  inflater.inflate(R.layout.photo_fragment, container, false);

        recyclerViewPhotos = (RecyclerView) rootView.findViewById(R.id.recyclerViewPhotos);
        try {

            if (productImages == null) throw new NullPointerException();

            ReviewImagesAdaptor reviewAdaptor = new ReviewImagesAdaptor(getActivity(), productImages);

            recyclerViewPhotos.setAdapter(reviewAdaptor);
            recyclerViewPhotos.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (NullPointerException ex) {
            Toast.makeText(getActivity(), "No Images Available", Toast.LENGTH_SHORT).show();
        }


        return rootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        productImages = null;

    }


}
