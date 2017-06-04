package com.softspec.transights.transights;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private RecyclerView mPlaceLists;
    private RecyclerAdapter recyclerAdapter;
    private MenuItem searchItem;

    private TransightsPresenter transightsPresenter;
    private RemoteDataRepository remoteDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);

        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_location:
                openDialogSelectLocation();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return true;
    }

    public void search(String keyword){
        transightsPresenter.search(keyword);
    }

    public void selectStation(String station){
        transightsPresenter.selectStation(station);
    }

    public void openDialogSelectLocation(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select station");
        View viewInflated = getLayoutInflater().inflate(R.layout.select_location, null);
        builder.setView(viewInflated);

        final AlertDialog alertDialog = builder.create();

        final Spinner stationSpinner = (Spinner) viewInflated.findViewById(R.id.station_select);

        List<String> list = new ArrayList<>();
        list.add("Select the station");
        for(Station s : remoteDataRepository.getStationList())
            list.add(s.getStationName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpinner.setAdapter(dataAdapter);

        Button goBtn = (Button) viewInflated.findViewById(R.id.go_btn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String select = stationSpinner.getSelectedItem().toString();
                if(!select.equalsIgnoreCase("Select the station")){
                    selectStation(select);
                    Toast.makeText(MainActivity.this, "Go to " + select + " station.", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else
                    Toast.makeText(MainActivity.this, "Please select the station!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

    public void setPlaceList(ArrayList<Place> placeList, ArrayList<Station> stationList){
        recyclerAdapter = new RecyclerAdapter(placeList, stationList, this);
        mPlaceLists.setAdapter(recyclerAdapter);
    }
}
