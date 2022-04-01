package com.example.wheregottimefind.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.backendAPI.AsyncUpdate;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.pojo.Product;
import com.example.wheregottimefind.data.pojo.Vendor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewReviewFragment extends Fragment {
    final static String TAG = "new_review_fragment";
    FloatingActionButton fab;
    EditText vendorLocationEdittext;
    EditText vendorPhoneEditText;

    public NewReviewFragment() {
        // Required empty public constructor
    }

    private static void updateVendors(String vendorName, ArrayAdapter<Vendor> adapter,
                                      AutoCompleteTextView vendorNameTextView) {
        BackendApi.getVendorsByVendorName(vendorName, new AsyncUpdate<Vendor>() {
            @Override
            public void updateOnDataReceived(Vendor[] vendorsData) {
                if (vendorsData != null) {
                    adapter.clear();
                    for (Vendor vendor: vendorsData) {
                        adapter.add(vendor);
                    }
                vendorNameTextView.refreshAutoCompleteResults();
                }
            }
        });
    }

    private static void updateProducts() {

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
        vendorLocationEdittext = rootView.findViewById(R.id.vendor_location);
        vendorPhoneEditText = rootView.findViewById(R.id.vendor_phone_no);


        // Hide FAB when leaving New Review page
        fab = getActivity().findViewById(R.id.fab);
        fab.hide();

        // Autocomplete field
        AutoCompleteTextView vendorNameTextView = rootView.findViewById(R.id.vendor_name);
        AutoCompleteTextView productNameTextView = rootView.findViewById(R.id.review_product_name);
        ArrayAdapter<Vendor> vendorAdapter = new ArrayAdapter<Vendor>(container.getContext(),
                android.R.layout.simple_list_item_1);
        ArrayAdapter<Product> productAdapter = new ArrayAdapter<>(container.getContext(),
                android.R.layout.simple_list_item_1);
        vendorNameTextView.setAdapter(vendorAdapter);
        productNameTextView.setAdapter(productAdapter);

        vendorNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateVendors(charSequence.toString(), vendorAdapter, vendorNameTextView);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Autofill item on click listener
        vendorNameTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vendor clicked_vendor = ((Vendor) adapterView.getItemAtPosition(i));

                // Fill in other fields
                vendorLocationEdittext.setText(clicked_vendor.getLocation());
                vendorPhoneEditText.setText(String.valueOf(clicked_vendor.getPhone_no()));
                vendorLocationEdittext.setEnabled(false);
                vendorPhoneEditText.setEnabled(false);

                // Set vendor id as tag to name field
                vendorNameTextView.setTag(Integer.valueOf(clicked_vendor.getId()));
                System.out.println("TAG null?: " + vendorNameTextView.getTag());

                // TODO: Set details for form submission
            }
        });

        //
        vendorNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vendorLocationEdittext.setEnabled(true);
                vendorPhoneEditText.setEnabled(true);
                vendorNameTextView.setTag(null);
                System.out.println("TAG null?: " + vendorNameTextView.getTag());
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