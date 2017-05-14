package com.softspec.transights.transights;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by macbook on 5/13/2017 AD.
 */

public class MyFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<Place, MyFirebaseRecyclerAdapter.MyFirebaseViewHolder>{

    public MyFirebaseRecyclerAdapter(Class modelClass, int modelLayout, Class viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public MyFirebaseRecyclerAdapter(Class modelClass, int modelLayout, Class viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        notifyDataSetChanged();
    }

    @Override
    protected void populateViewHolder(MyFirebaseViewHolder viewHolder, Place model, int position) {
        viewHolder.setPlaceName(model.getPlaceName());
        viewHolder.setStationName(model.getStationName());
    }

    public static class MyFirebaseViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MyFirebaseViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPlaceName(String placeName){
            TextView place_name = (TextView) mView.findViewById(R.id.place_name);
            place_name.setText(placeName);
        }

        public void setStationName(String stationName){
            TextView station_name = (TextView) mView.findViewById(R.id.place_station);
            station_name.setText(stationName);
        }
    }
}
