package com.bphillips91.imagesearch;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "TAG";
    Button mButton;
    EditText mSearch;
    RequestQueue photoRequest;
    List<Photo> photoList;
    PhotoAdapter mAdapter;
    GridLayoutManager gridManager;
    RecyclerView mRecycler;

    public String parseME;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseME = null;

        mSearch = (EditText)findViewById(R.id.search_et);
        mButton = (Button) findViewById(R.id.search_button);
        photoRequest = Volley.newRequestQueue(this);
        mRecycler = (RecyclerView) findViewById(R.id.photo_recycler);
        gridManager = new GridLayoutManager(this, 3);
        mRecycler.setLayoutManager(gridManager);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = mSearch.getText().toString();
                if (!s.equals("")) {
                    mButton.setVisibility(View.VISIBLE);
                } else
                    mButton.setVisibility(View.INVISIBLE);
            }
        };
        mSearch.addTextChangedListener(textWatcher);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = mSearch.getText().toString();

                String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=2fbd51c2f84ff08464b5a5bdbe6e89be&tags="+keyWord+"&format=json&nojsoncallback=1";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                           parseJSON(response.toString());
                        Log.d(TAG, "onResponse: "+response.toString());


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", "Error");

                    }
                });
                photoRequest.add(jsObjRequest);

            }

        });
    }
    public void parseJSON(String s){
        parseME = s;
        photoList = new ArrayList<Photo>();
        try {
            JSONObject parseMe = new JSONObject(s);
            JSONObject photos = parseMe.getJSONObject("photos");

            JSONArray photoArray = photos.getJSONArray("photo");
            for (int i = 0; i < photoArray.length(); i++) {
                JSONObject photo = photoArray.getJSONObject(i);

                String photoID = photo.getString("id");
                String title = photo.getString("title");
                String farmID = photo.getString("farm");
                String serverID = photo.getString("server");
                String secret = photo.getString("secret");
                Photo mPhoto;
                mPhoto = new Photo(farmID, serverID, photoID, secret, title);
                photoList.add(mPhoto);

            }

            mAdapter = new PhotoAdapter(photoList, MainActivity.this);
            mRecycler.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(parseME!= null) {
            outState.putString("JSON", parseME);
        }
        Log.d(TAG, "onSaveInstanceState: "+parseME);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: "+savedInstanceState.getString("JSON"));
            if(savedInstanceState.getString("JSON")!= null) {
                String jsonString = savedInstanceState.getString("JSON");
                parseJSON(jsonString);
            }

    }
}



