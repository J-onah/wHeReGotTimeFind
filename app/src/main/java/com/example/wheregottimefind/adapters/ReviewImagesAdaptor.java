package com.example.wheregottimefind.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheregottimefind.R;



public class ReviewImagesAdaptor extends RecyclerView.Adapter<ReviewImagesAdaptor.ReviewViewHolder> {

    String[] iProductImages;

    float screen_width;

    Context context;

    public ReviewImagesAdaptor(Context page, String[] productImages){
        this.context = page;
        this.iProductImages = productImages;
    }





    //RecyclerView calls this method whenever it needs to create a new ViewHolder.
    //The method creates and initializes the ViewHolder and its associated View,
    //but does not fill in the view's contents—the ViewHolder has not yet been bound to
    //specific data.

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photos_template, parent, false);

        screen_width = view.getWidth();

        return new ReviewViewHolder(view);
    }




    //RecyclerView calls this method to associate a ViewHolder with data. The method fetches the appropriate data
    //and uses the data to fill in the view holder's layout.
    //For example, if the RecyclerView displays a list of names,
    //the method might find the appropriate name in the list and fill in the view holder's
    //TextView widget.
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        try{
            if (iProductImages != null) {
                byte[] decodedString = Base64.decode(iProductImages[position].substring(22), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.giantProductImage.setImageBitmap(decodedByte);
            }
            else throw new IndexOutOfBoundsException();

        }
        catch(IndexOutOfBoundsException ex){
            holder.giantProductImage.setImageResource(R.drawable.blank);
        }

    }





    //RecyclerView calls this method to get the size of the data set.
    //For example, in an address book app, this might be the total number of addresses.
    //RecyclerView uses this to determine when there are no more items that can be displayed.

    @Override
    public int getItemCount() {

        return iProductImages.length;

    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        ImageView giantProductImage;



        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            giantProductImage = itemView.findViewById(R.id.GiantProductImage);


        }

    }


}


