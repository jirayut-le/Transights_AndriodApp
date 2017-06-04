package com.softspec.transights.transights;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private DatabaseReference mDatabase;

    private ArrayList<Place> mPlace = new ArrayList<>();

    private ArrayList<Station> stationList  = new ArrayList<>();

    private RecyclerView mPlaceLists;
    private RecyclerAdapter recyclerAdapter;

    private MenuItem searchItem, locationItem;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("RUN", "ON CREATE");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mPlaceLists = (RecyclerView) findViewById(R.id.place_recycleView);
        mPlaceLists.setHasFixedSize(true);
        mPlaceLists.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter(mPlace, stationList, this);
        mPlaceLists.setAdapter(recyclerAdapter);

        addChildStation();
        addChildPlace();

    }

    private void addChildStation(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("station");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                loadStation(dataSnapshot);
                recyclerAdapter.notifyDataSetChanged();
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

    private void addChildPlace(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("places");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                loadPlace(dataSnapshot);
                recyclerAdapter.notifyDataSetChanged();
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
        stationList.add(new Station(dataSnapshot.child("stationName").getValue(String.class), 0, 0));
    }

    private void loadPlace(DataSnapshot dataSnapshot){
        String stationName = dataSnapshot.child("stationName").getValue(String.class);
        String placeID = dataSnapshot.child("placeID").getValue(String.class);
        String placeName = dataSnapshot.child("placeName").getValue(String.class);
        String description = dataSnapshot.child("description").getValue(String.class);
        String imgSrc = dataSnapshot.child("imgSrc").getValue(String.class);

        Place p = new Place(placeID, placeName, description, imgSrc);

        mPlace.add(p);

        for(Station s : stationList)
            if(s.getStationName().equalsIgnoreCase(stationName))
                s.addPlace(p);
    }

    private void loadTimeAndPrice(DataSnapshot dataSnapshot){
        DataSnapshot tmpDataSnapshot = dataSnapshot.child("priceandtime");
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);

        searchItem = menu.findItem(R.id.action_search);
        locationItem = menu.findItem(R.id.action_location);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        query = query.toLowerCase();
//        Query newDatabase =  mDatabase.orderByChild("placeName").startAt(query).endAt(query.toLowerCase());
//        setmPlaceListByFilter(newDatabase);
        search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return true;
    }

    public void search(String newText){
//        newText = newText.toLowerCase();
//        ArrayList<Place> newList = new ArrayList<>();
//        for(Place place : mPlace){
//            String placeName = place.getPlaceName().toLowerCase();
//            String stationName = place.getStationName().toLowerCase();
//            if(placeName.contains(newText) || stationName.contains(newText)){
//                newList.add(place);
//            }
//        }
//        recyclerAdapter.setFilter(newList);
//        mPlaceLists.setAdapter(recyclerAdapter);
    }
}
