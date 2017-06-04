package com.softspec.transights.transights;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by macbook on 6/4/2017 AD.
 */

public class RemoteDataRepository extends Observable {

    private ArrayList<Place> placeList;
    private ArrayList<Station> stationList;

    private ArrayList<Place> result;

    private DatabaseReference mDatabase;

    private static RemoteDataRepository remoteDataRepository;

    private RemoteDataRepository(){
        placeList = new ArrayList<>();
        stationList = new ArrayList<>();
        result = new ArrayList<>();
    }

    public static RemoteDataRepository getInstance(){
        if(remoteDataRepository == null)
            remoteDataRepository = new RemoteDataRepository();
        return remoteDataRepository;
    }

    public void search(String keyword){
        keyword = keyword.toLowerCase();
        result.clear();
        for(Station s : stationList){
            for(Place p : s.getPlaceList()){
                if(s.getStationName().toLowerCase().contains(keyword) || p.getPlaceName().toLowerCase().contains(keyword)){
                    result.add(p);
                }
            }
        }
        setChanged();
        notifyObservers();
    }


    public void fetchAllStation(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("station");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                loadStation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void fetchAllPlace(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("places");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                loadPlace(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fetchAllPriceAndTime(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("priceandtime");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                loadTimeAndPrice(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadStation(DataSnapshot dataSnapshot){
        String stationName = dataSnapshot.child("stationName").getValue(String.class);
        stationList.add(new Station(stationName, 0, 0));

        if(stationName.equalsIgnoreCase("bearing")){
            setChanged();
            notifyObservers("station");
        }

    }

    private void loadPlace(DataSnapshot dataSnapshot){
        String stationName = dataSnapshot.child("stationName").getValue(String.class);
        String placeID = dataSnapshot.child("placeID").getValue(String.class);
        String placeName = dataSnapshot.child("placeName").getValue(String.class);
        String description = dataSnapshot.child("description").getValue(String.class);
        String imgSrc = dataSnapshot.child("imgSrc").getValue(String.class);

        Place p = new Place(placeID, placeName, description, imgSrc);

        placeList.add(p);
        result.add(p);

        for(Station s : stationList)
            if(s.getStationName().equalsIgnoreCase(stationName))
                s.addPlace(p);

        if(placeID.equalsIgnoreCase("B22-2")){
            setChanged();
            notifyObservers("place");
        }
    }

    private void loadTimeAndPrice(DataSnapshot dataSnapshot){
        String deptStation = dataSnapshot.child("depart").getValue(String.class);
        String arrtStation = dataSnapshot.child("arrive").getValue(String.class);
        int price = Integer.parseInt(dataSnapshot.child("price").getValue(String.class));
        int time = Integer.parseInt(dataSnapshot.child("time").getValue(String.class));

        for(Station s: stationList){
            if(s.getStationName().equalsIgnoreCase(deptStation)) {
                s.addArriveStation(new Station(arrtStation, price, time));
                break;
            }
        }

        if(deptStation.equalsIgnoreCase("bearing") && arrtStation.equalsIgnoreCase("bearing")){
            setChanged();
            notifyObservers();
        }
    }

    public ArrayList<Place> getPlaceList() {
        return result;
    }

    public ArrayList<Station> getStationList() {
        return stationList;
    }
}
