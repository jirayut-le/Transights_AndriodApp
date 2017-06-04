package com.softspec.transights.transights;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

    private MenuItem searchItem;

    private TransightsPresenter transightsPresenter;
    private RemoteDataRepository remoteDataRepository;

    private SectionPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Home home;
    private Estimate estimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = new Home();
        estimate = new Estimate();

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        setupViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setUpTabLayout();

        loadFirebase();
    }

    public void setupViewPager(){
        viewPager = (ViewPager) findViewById(R.id.container);
        sectionPagerAdapter.addFragment(home);
        sectionPagerAdapter.addFragment(estimate);
        viewPager.setAdapter(sectionPagerAdapter);
    }

    public void setUpTabLayout(){
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_access_time_black_24dp);
        tabLayout.getTabAt(0).setText("Home");
        tabLayout.getTabAt(1).setText("Estimate");
    }

    public void loadFirebase(){
        remoteDataRepository = RemoteDataRepository.getInstance();
        transightsPresenter = new TransightsPresenter(remoteDataRepository, home);
        transightsPresenter.initialize();
    }

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

    public void showAll(){
        transightsPresenter.showAll();
    }

    public void openDialogSelectLocation(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select station");
        View viewInflated = getLayoutInflater().inflate(R.layout.select_location, null);
        builder.setView(viewInflated);

        final AlertDialog alertDialog = builder.create();

        final Spinner stationSpinner = (Spinner) viewInflated.findViewById(R.id.station_select);

        List<String> list = new ArrayList<>();
        list.add("Show All");
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
                if(!select.equalsIgnoreCase("Show All")){
                    selectStation(select);
                    Toast.makeText(MainActivity.this, "Go to " + select + " station.", Toast.LENGTH_SHORT).show();
                } else
                    showAll();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
