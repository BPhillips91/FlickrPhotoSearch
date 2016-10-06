package com.bphillips91.imagesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by brendan on 10/5/16.
 */

public class PhotoDetail extends AppCompatActivity {
    ImageView singleImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail);

        singleImage = (ImageView) findViewById(R.id.single_photo);

        String imgURL = "https://farm" +getIntent().getStringExtra("farmID")+".staticflickr.com/"+getIntent().getStringExtra("serverID")+"/"+getIntent().getStringExtra("photoID")+
                "_"+getIntent().getStringExtra("secret")+"_b.jpg";


        Log.d("URL", "onCreate: "+ imgURL);



        Picasso.with(PhotoDetail.this)
                .load(imgURL)
                .into(singleImage);

        setTitle(getIntent().getStringExtra("title"));



    }
}
