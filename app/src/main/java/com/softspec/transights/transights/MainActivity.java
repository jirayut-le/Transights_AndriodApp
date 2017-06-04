package com.softspec.transights.transights;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private RecyclerView mPlaceLists;
    private RecyclerAdapter recyclerAdapter;

    private MenuItem searchItem, locationItem;

    private TransightsPresenter transightsPresenter;
    private RemoteDataRepository remoteDataRepository;


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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initRecycleView();
    }

    public void initRecycleView(){

        mPlaceLists = (RecyclerView) findViewById(R.id.place_recycleView);
        mPlaceLists.setHasFixedSize(true);
        mPlaceLists.setLayoutManager(new LinearLayoutManager(this));

        remoteDataRepository = RemoteDataRepository.getInstance();
        transightsPresenter = new TransightsPresenter(remoteDataRepository, this);
        transightsPresenter.initialize();
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

    public void setPlaceList(ArrayList<Place> placeList, ArrayList<Station> stationList){
        recyclerAdapter = new RecyclerAdapter(placeList, stationList, this);
        mPlaceLists.setAdapter(recyclerAdapter);
    }

    public void search(String keyword){
        transightsPresenter.search(keyword);
    }
}
