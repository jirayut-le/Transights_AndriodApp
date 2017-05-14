package com.softspec.transights.transights;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 5/14/2017 AD.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    ArrayList<Place> placeList = new ArrayList<>();
    Context context;

    public RecyclerAdapter(ArrayList<Place> placeList, Context context) {
        this.placeList = placeList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_row, parent, false);
        return new MyViewHolder(view, context, placeList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.setPlaceName(placeList.get(position).getPlaceName());
        holder.setStationName(placeList.get(position).getStationName());
        holder.setImgsrc(placeList.get(position).getImgSrc());

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView placeName, stationName;
        ImageView imgsrc;
        ArrayList<Place> places;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<Place> places) {
            super(itemView);
            this.places = places;
            this.context = context;
            itemView.setOnClickListener(this);
        }

        public void setPlaceName(String placeName){
            this.placeName = (TextView) itemView.findViewById(R.id.place_name);
            this.placeName.setText(placeName);
        }

        public void setStationName(String stationName){
            this.stationName = (TextView) itemView.findViewById(R.id.place_station);
            this.stationName.setText(stationName);
        }

        public void setImgsrc(String src){
            this.imgsrc = (ImageView) itemView.findViewById(R.id.place_img);
            Picasso.with(context).load(src).into(this.imgsrc);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Place place = this.places.get(pos);
            Intent intent = new Intent(this.context, PlaceDetail.class);
            intent.putExtra("placeName", place.getPlaceName());
            intent.putExtra("stationName", place.getStationName());
            intent.putExtra("imgsrc", place.getImgSrc());

            this.context.startActivity(intent);

        }
    }

    public void setFilter(ArrayList<Place> arrayList){

        placeList = new ArrayList<>();
        placeList.addAll(arrayList);
        notifyDataSetChanged();
    }
}