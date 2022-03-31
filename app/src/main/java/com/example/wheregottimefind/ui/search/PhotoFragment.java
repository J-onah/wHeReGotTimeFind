package com.example.wheregottimefind.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.PostReview_NotNeeded;
import com.example.wheregottimefind.R;
import com.example.wheregottimefind.ReviewAdaptor_ToCommit;
import com.example.wheregottimefind.ReviewImagesAdaptor;
import com.example.wheregottimefind.pojo.Review;
import com.example.wheregottimefind.pojo.Vendor;

import java.util.ArrayList;

///////////////////////////////////////////// TO COMMIT 31-03-22
public class PhotoFragment extends Fragment{


    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link com.example.wheregottimefind.ui.search.PhotoFragment#newInstance} factory method to
     * create an instance of this fragment.
     */



        /////////// TO COMMIT 29-03-22, MIGHT NOT NEED


        RecyclerView recyclerViewPhotos;

        String[] ProductImages;


        Button PhotoFragmentOutButton;





        final static String TAG = "result_fragment";
        String vendor_name;

        public PhotoFragment() {
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
                ProductImages = getArguments().getStringArray("ProductImages");
                Log.i(TAG, String.valueOf(vendor_name));
            } else {
                Log.i(TAG, "No arguments received");
            }
            // Inflate the layout for this fragment
            View rootView =  inflater.inflate(R.layout.photo_fragment, container, false);






        /* /////////////// GENERAL CODES

        //ArrayList<FullReview> ListOfFullReviewObj = FullReviewData.getAllReviewsByVendor(vendor_name);

        ProductImages.add(.);

        for(FullReview eachFullReview: ListOfFullReviewObj) {

        }
        */


            PhotoFragmentOutButton = rootView.findViewById(R.id.PhotoFragmentOutButton);

            recyclerViewPhotos = (RecyclerView) rootView.findViewById(R.id.recyclerViewPhotos);
            try {

                if (ProductImages == null) throw new NullPointerException();

                ReviewImagesAdaptor reviewAdaptor = new ReviewImagesAdaptor(getActivity(), ProductImages);

                recyclerViewPhotos.setAdapter(reviewAdaptor);
                recyclerViewPhotos.setLayoutManager(new LinearLayoutManager(getActivity()));
                //https://stackoverflow.com/questions/26621060/display-a-recyclerview-in-fragment




            } catch (NullPointerException ex) {
                //Bundle args = new Bundle();
                //args.putString("vendor_name", vendor_name);
                //Navigation.findNavController(rootView).navigate(R.id.action_navigation_search_to_resultFragment, args);
                Toast.makeText(getActivity(), "No Images Available", Toast.LENGTH_LONG).show();
            }

            //https://www.youtube.com/watch?v=18VcnYN5_LM
            /////////////////////////////////////////////////////////////////////////////////////

            PhotoFragmentOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putString("vendor_name", vendor_name);
                    Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_resultFragment, args);
                }
            });





            return rootView;



        }


}




