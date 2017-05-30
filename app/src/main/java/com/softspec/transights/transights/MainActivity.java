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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private TextView mTextMessage;

    private DatabaseReference mDatabase;

    private ArrayList<Place> mPlace = new ArrayList<>();

    private RecyclerView mPlaceLists;
    private RecyclerAdapter recyclerAdapter;
//    private MyFirebaseRecyclerAdapter firebaseRecyclerAdapter;

    private MenuItem searchItem, locationItem;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("places");


        mPlaceLists = (RecyclerView) findViewById(R.id.place_recycleView);
        mPlaceLists.setHasFixedSize(true);
        mPlaceLists.setLayoutManager(new LinearLayoutManager(this));


        recyclerAdapter = new RecyclerAdapter(mPlace, this);
        mPlaceLists.setAdapter(recyclerAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String placeID = dataSnapshot.child("palceID").getValue(String.class);
                String placeName = dataSnapshot.child("PlaceName").getValue(String.class);
                String stationName = dataSnapshot.child("stationName").getValue(String.class);
                String imgSrc = dataSnapshot.child("imgsrc").getValue(String.class);
                mPlace.add(new Place(placeID, placeName, stationName, imgSrc));
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

        mDatabase.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                return null;
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                Log.d("loadDatabase", "load complete");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);

        searchItem = menu.findItem(R.id.action_search);
        locationItem = menu.findItem(R.id.action_location);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);


//        searchView.setOnClickListener(new SearchView.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("search view : ", "open");
//            }
//        });
//
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("view : ", "open");
//            }
//        });
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                Log.d("search view : ", "close");
//                return true;
//            }
//        });


//        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Log.d("menu item : ", "open");
//                locationItem.setVisible(false);
//                return true;
//            }
//        });

//        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                locationItem.setVisible(false);
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                locationItem.setVisible(true);
//                searchItem.setVisible(true);
//                return true;
//            }
//        });

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
        newText = newText.toLowerCase();
        ArrayList<Place> newList = new ArrayList<>();
        for(Place place : mPlace){
            String placeName = place.getPlaceName().toLowerCase();
            String stationName = place.getStationName().toLowerCase();
            if(placeName.contains(newText) || stationName.contains(newText)){
                newList.add(place);
            }
        }
        recyclerAdapter.setFilter(newList);
        mPlaceLists.setAdapter(recyclerAdapter);
    }
}
