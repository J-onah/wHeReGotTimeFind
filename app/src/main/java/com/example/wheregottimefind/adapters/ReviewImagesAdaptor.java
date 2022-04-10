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

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photos_template, parent, false);

        screen_width = view.getWidth();

        return new ReviewViewHolder(view);
    }

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


