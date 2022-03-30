package com.example.wheregottimefind.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wheregottimefind.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {
    final static String TAG = "result_fragment";
    int id;
    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(int id) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
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
            id = getArguments().getInt("id");
            Log.i(TAG, String.valueOf(id));
        } else {
            Log.i(TAG, "No arguments received");
        }
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_result, container, false);

        TextView result_fragment_text = rootView.findViewById(R.id.result_fragment_text);
        result_fragment_text.setText("Result fragment got id: " + String.valueOf(id));

        return rootView;
    }
}