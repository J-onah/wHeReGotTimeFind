package com.example.wheregottimefind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.wheregottimefind.pojo.Vendor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewReviewFragment extends Fragment {
    final static String TAG = "new_review_fragment";
    FloatingActionButton fab;
    EditText vendor_location_edittext;
    EditText vendor_phone_no_edittext;

    private static final ArrayList<Vendor> vendors = new ArrayList<>();


    public NewReviewFragment() {
        // Required empty public constructor
        // For testing only
        this.updateVendors();
    }

    private static void updateVendors() {
        // TODO: Update to call API with query
        // THIS IS MOCK DATA
        vendors.clear();
        vendors.add(new Vendor("vendor1", "90 somapah road", 91234567));
        vendors.add(new Vendor("goodvendor", "1 jurong road", 90909090));
        vendors.add(new Vendor("badvendor", "30 Kallang Road", 65656565));
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewReviewFragment newInstance(String param1, String param2) {
        NewReviewFragment fragment = new NewReviewFragment();
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
        View rootView;

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_newreview, container, false);

        // Get reference to edittext
        vendor_location_edittext = rootView.findViewById(R.id.vendor_location);
        vendor_phone_no_edittext = rootView.findViewById(R.id.vendor_phone_no);


        // Hide FAB when leaving New Review page
        fab = getActivity().findViewById(R.id.fab);
        fab.hide();

        // Autocomplete field
        AutoCompleteTextView vendor_name = rootView.findViewById(R.id.vendor_name);
        ArrayAdapter<Vendor> adapter = new ArrayAdapter<Vendor>(container.getContext(),
                android.R.layout.simple_list_item_1, vendors);
        vendor_name.setAdapter(adapter);

        // Autofill item on click listener
        vendor_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vendor clicked_vendor = ((Vendor) adapterView.getItemAtPosition(i));
                Log.i(TAG, "Autofill item clicked: " + clicked_vendor);

                // Fill in other fields
                vendor_location_edittext.setText(clicked_vendor.getLocation());
                vendor_phone_no_edittext.setText(String.valueOf(clicked_vendor.getPhone_no()));
                vendor_location_edittext.setEnabled(false);
                vendor_phone_no_edittext.setEnabled(false);

                // TODO: Set details for form submission
            }
        });

        //
        vendor_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vendor_location_edittext.setEnabled(true);
                vendor_phone_no_edittext.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                return;
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Show FAB when leaving New Review page
        fab.show();
    }
}