package com.example.wheregottimefind.ui;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.support.v4.media.MediaBrowserCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.wheregottimefind.R;
import com.example.wheregottimefind.backendAPI.AsyncUpdate;
import com.example.wheregottimefind.backendAPI.BackendApi;
import com.example.wheregottimefind.data.pojo.ImageArray;
import com.example.wheregottimefind.data.pojo.Product;
import com.example.wheregottimefind.data.pojo.Review;
import com.example.wheregottimefind.data.pojo.Vendor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewReviewFragment extends Fragment {
    final static String TAG = "new_review_fragment";
    private final int CAMERA_REQUEST = 8888;
    FloatingActionButton fab;
    EditText vendorLocationEdittext;
    EditText vendorPhoneEditText;
    Button submitButton;
    Button takephoto;
    ImageView imageIV;

    public NewReviewFragment() {
        // Required empty public constructor
    }

    private String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap compressImage(Bitmap image , long maxSize) {
        int byteCount = image.getByteCount();
        Log.i("yc压缩图片","压缩前大小"+byteCount);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = null;
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        while (baos.toByteArray().length  > maxSize) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            if(options == 1){
                break;
            }else if (options <= 10) {
                options -= 1;
            } else {
                options -= 10;
            }
        }
        byte[] bytes = baos.toByteArray();
        if (bytes.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            int byteCount1 = bitmap.getByteCount();
            Log.i("yc压缩图片","压缩后大小"+byteCount1);
        }
        return bitmap;
    }



    private void updateVendors(String vendorName, ArrayAdapter<Vendor> adapter,
                                      AutoCompleteTextView vendorNameTextView) {
        BackendApi.getVendorsByVendorName(getActivity(), vendorName, new AsyncUpdate<Vendor[]>() {
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

    private void updateProducts(String productName, ArrayAdapter<Product> adapter,
                               AutoCompleteTextView productNameTextview) {
        BackendApi.getProductsByProductName(getActivity(), productName, new AsyncUpdate<Product[]>() {
            @Override
            public void updateOnDataReceived(Product[] productData) {
                if (productData != null) {
                    adapter.clear();
                    for (Product product: productData) {
                        adapter.add(product);
                    }
                    productNameTextview.refreshAutoCompleteResults();
                }
            }
        });
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


    // for permission check
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Permission Required");
                    dialog.setTitle("Turn on the camera permission to take photo.");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    dialog.show();
                }
            });



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Permission check
        if (ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA);
        }

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
        submitButton = rootView.findViewById(R.id.submit_review);
        takephoto = rootView.findViewById(R.id.takephoto);
        imageIV = (ImageView) rootView.findViewById(R.id.imageIV);


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

        // Autofill item on click listener
        vendorNameTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            Vendor clickedProduct = ((Vendor) adapterView.getItemAtPosition(i));

            // Fill in other fields
            vendorLocationEdittext.setText(clickedProduct.getLocation());
            Long vendorPhoneNo = clickedProduct.getPhone_no();
            if (vendorPhoneNo <= 0) {
                vendorPhoneEditText.setText("Phone number unknown!");
            } else {
                vendorPhoneEditText.setText(String.valueOf(vendorPhoneNo));
            }
            vendorLocationEdittext.setEnabled(false);
            vendorPhoneEditText.setEnabled(false);

            // Set vendor id as tag to name field
            vendorNameTextView.setTag(Integer.valueOf(clickedProduct.getId()));
        });

        productNameTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            Product clickedProduct = ((Product) adapterView.getItemAtPosition(i));

            // Set vendor id as tag to name field
            productNameTextView.setTag(Integer.valueOf(clickedProduct.getId()));
            System.out.println("TAG null?: " + productNameTextView.getTag());
        });

        vendorNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                vendorLocationEdittext.setEnabled(true);
                vendorPhoneEditText.setEnabled(true);
                vendorNameTextView.setTag(null);

                // Prevents update when clicking autofill, which triggers the dropdown again
                if (start != 0) {
                    updateVendors(charSequence.toString(), vendorAdapter, vendorNameTextView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        productNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Prevents update when clicking autofill, which triggers the dropdown again
                if (start != 0) {
                    updateProducts(charSequence.toString(), productAdapter, productNameTextView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                return;
            }
        });


        takephoto.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             TakePhoto();
                                         }
                                     });


        // Handle submission
        submitButton.setOnClickListener(view -> {
            Integer existingProductId = null;
            String newProductName = null;
            Integer existingVendorId = null;
            String newVendorName = null;
            String newVendorLocation = null;
            Long newVendorPhoneNo = null;
            RequestBody imagesDataArr = null;
            String existingTagIdsArr = null;
            String newTagNamesArr = null;
            Integer rating = null;
            Integer unitsPurchased = null;
            String unit = null;
            Double pricePerUnit = null;
            String comments = null;

            try {
                // Set loading to true
                submitButton.setText("Submitting...");

                // Get all details
                // -- Vendor
                if (vendorNameTextView.getTag() != null) {
                    // Existing product
                    existingVendorId = (int) vendorNameTextView.getTag();
                } else {
                    // New product
                    newVendorName = vendorNameTextView.getText().toString();
                    newVendorLocation = vendorLocationEdittext.getText().toString();
                    String newVendorPhoneNoString = vendorPhoneEditText.getText().toString();
                    if (newVendorPhoneNoString.isEmpty()) {
                        newVendorPhoneNo = Long.valueOf(0);
                    } else {
                        newVendorPhoneNo = Long.valueOf(newVendorPhoneNoString);
                    }

//                    System.out.println("vendorphone" + newVendorPhoneNo);
                    if (newVendorName.isEmpty() || newVendorLocation.isEmpty()) {
                        throw new IllegalArgumentException("Vendor details must not be empty!");
                    };
                }

                // -- Product
                // Check if tag is null
                if (productNameTextView.getTag() != null) {
                    // Existing product
                    existingProductId = (int) productNameTextView.getTag();
                } else {
                    // New product
                    newProductName = productNameTextView.getText().toString();
                    if (newProductName.isEmpty()) {
                        throw new IllegalArgumentException("Product name must not be empty!");
                    };
                }

                BitmapDrawable drawable = (BitmapDrawable) imageIV.getDrawable();
                if (drawable != null) {
                    Bitmap bitmap = drawable.getBitmap();
                    bitmap = compressImage(bitmap , 32768);
                    imagesDataArr = RequestBody.create(MediaType.parse("text/plain"), (bitmapToBase64(bitmap)));
                    System.out.println(imagesDataArr);
                } else {
                    imagesDataArr = RequestBody.create(MediaType.parse("text/plain"), "");
                }

                // -- Review Details
                // Rating
                RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);
                rating = (int) ratingBar.getRating();

                // Units Purchased/Unit/Price per unit
                EditText unitsPurchasedEditText = rootView.findViewById(R.id.review_units_purchased);
                EditText unitEditText = rootView.findViewById(R.id.review_unit);
                EditText pricePerUnitEditText = rootView.findViewById(R.id.review_price_per_unit);

                String unitsPurchasedString = unitsPurchasedEditText.getText().toString();
                String unitString = unitEditText.getText().toString();
                String pricePerUnitString = pricePerUnitEditText.getText().toString();

                if (unitsPurchasedString.isEmpty() || unitString.isEmpty() || pricePerUnitString.isEmpty()) {
                    throw new IllegalArgumentException("Price and units cannot be empty!");
                } else {
                    unitsPurchased = Integer.valueOf(unitsPurchasedString);
                    unit = unitString;
                    pricePerUnit = Double.valueOf(pricePerUnitString);
                }

                // Comments
                EditText commentEditText = rootView.findViewById(R.id.review_comments);
                comments = commentEditText.getText().toString();

                // Call postReview
                BackendApi.postReview(getActivity(), existingProductId, newProductName, existingVendorId,
                        newVendorName, newVendorLocation, newVendorPhoneNo,
                        imagesDataArr, existingTagIdsArr, newTagNamesArr,
                        rating, unitsPurchased, unit, pricePerUnit, comments,
                        new AsyncUpdate<Review>() {

                            @Override
                            public void updateOnDataReceived(Review array) {
                                Toast.makeText(getActivity(),
                                        "Review posted!", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).popBackStack();
                            }
                        });
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                submitButton.setText(R.string.submit_review);
            }
        });

        return rootView;
    }


    private void TakePhoto() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST
                && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            System.out.println(photo);
            imageIV.setImageBitmap(photo);
        }
    }


        @Override
    public void onPause() {
        super.onPause();
        // Show FAB when leaving New Review page
        fab.show();
    }

}