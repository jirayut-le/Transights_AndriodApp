package com.softspec.transights.transights;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlaceDetail extends AppCompatActivity {


    private TextView placeName, stationName, description;
    private ImageView placeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        placeName = (TextView) findViewById(R.id.place_detail_name);
        stationName = (TextView) findViewById(R.id.place_detail_station);
        placeImg = (ImageView) findViewById(R.id.place_detail_img);
        description = (TextView) findViewById(R.id.place_detail_description);

        placeName.setText(getIntent().getStringExtra("placeName"));
        stationName.setText(getIntent().getStringExtra("stationName"));
        description.setText(getIntent().getStringExtra("description"));
        Picasso.with(this).load(getIntent().getStringExtra("imgsrc")).into(placeImg);
    }
}
