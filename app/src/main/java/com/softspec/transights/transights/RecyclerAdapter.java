package com.softspec.transights.transights;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    ArrayList<Station> stationList = new ArrayList<>();
    Context context;

    MyViewHolder myViewHolder;

    public RecyclerAdapter(ArrayList<Place> placeList,ArrayList<Station> stations, Context context) {
        this.placeList = placeList;
        this.stationList = stations;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_row, parent, false);
        myViewHolder = new MyViewHolder(view, context, placeList, stationList);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String placeName = placeList.get(position).getPlaceName();

        holder.setPlaceName(placeName);
        holder.setImgsrc(placeList.get(position).getImgSrc());
        String stationName  = "";
        for(Station s : stationList){
            for(Place p : s.getPlaceList()){
                if(p.getPlaceName().equalsIgnoreCase(placeName)) {
                    stationName = s.getStationName();
                    break;
                }
            }
        }
        holder.setStationName(stationName);

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView placeName, stationName;
        ImageView imgsrc;
        ArrayList<Place> places;
        ArrayList<Station> stationList;
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<Place> places, ArrayList<Station> stations) {
            super(itemView);
            this.places = places;
            this.stationList = stations;
            this.context = context;
            itemView.setOnClickListener(this);
        }

        public void setPlaceName(String placeName){
            this.placeName = (TextView) itemView.findViewById(R.id.place_name);
            this.placeName.setText(placeName);
        }

        public void setStationName(String stationName){
            this.stationName = (TextView) itemView.findViewById(R.id.place_station);
            this.stationName.setText("@"+ stationName + " Station");
        }

        public void setImgsrc(String src){
            this.imgsrc = (ImageView) itemView.findViewById(R.id.place_img);
            Picasso.with(context).load(src).into(this.imgsrc);
        }

        private String getStationName(String placeName){
            for(Station s : stationList) {
                for (Place p : s.getPlaceList()) {
                    if (p.getPlaceName().equalsIgnoreCase(placeName))
                        return s.getStationName();
                }
            }
            return null;
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Place place = this.places.get(pos);
            Intent intent = new Intent(this.context, PlaceDetail.class);
            intent.putExtra("placeName", place.getPlaceName());
            intent.putExtra("stationName", getStationName(place.getPlaceName()));
            intent.putExtra("description", place.getDescription());
            intent.putExtra("imgsrc", place.getImgSrc());

            this.context.startActivity(intent);

        }
    }
}
