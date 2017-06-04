package com.softspec.transights.transights;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by macbook on 6/5/2017 AD.
 */

public class Home extends Fragment {

    private RecyclerView mPlaceLists;
    private RecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mPlaceLists = (RecyclerView) v.findViewById(R.id.place_recycleView);
        mPlaceLists.setHasFixedSize(true);
        mPlaceLists.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    public void setPlaceList(ArrayList<Place> placeList, ArrayList<Station> stationList){
        recyclerAdapter = new RecyclerAdapter(placeList, stationList, getContext());
        mPlaceLists.setAdapter(recyclerAdapter);
    }
}
