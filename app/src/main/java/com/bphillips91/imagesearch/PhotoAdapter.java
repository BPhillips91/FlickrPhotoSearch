package com.bphillips91.imagesearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brendan on 10/5/16.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    LayoutInflater mInflater;
    List<Photo> photoList;
    Context mContext;

    public PhotoAdapter(List<Photo> photoList, Context context) {
        this.mContext = context;
        this.photoList = photoList;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.from(parent.getContext()).inflate(R.layout.photo_item,
                parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(v, photoList, mContext);
        return holder;


    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        String imgURL = "https://farm" + photoList.get(position).getFarmID() + ".staticflickr.com/" + photoList.get(position).getServerID() + "/" + photoList.get(position).getPhotoID() +
                "_" + photoList.get(position).getSecret() + "_m.jpg";

        Log.d("RECYCLERVIEW", "onBindViewHolder: " + imgURL);
        Picasso.with(this.mContext)
                .load(imgURL)
                .into(holder.mPhoto);


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mPhoto;
        Context mContext;
        List<Photo> photoList = new ArrayList<>();

        public PhotoViewHolder(View itemView, List<Photo> mPhotoList, Context context) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.photo_image);
            this.photoList = mPhotoList;
            this.mContext = context;
            mPhoto.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Photo clickedPhoto = this.photoList.get(position);
            Intent intent = new Intent(this.mContext, PhotoDetail.class);

            intent.putExtra("photoID", clickedPhoto.getPhotoID());
            intent.putExtra("title", clickedPhoto.getTitle());
            intent.putExtra("farmID", clickedPhoto.getFarmID());
            intent.putExtra("serverID", clickedPhoto.getServerID());
            intent.putExtra("secret", clickedPhoto.getSecret());
            this.mContext.startActivity(intent);

        }
    }
}