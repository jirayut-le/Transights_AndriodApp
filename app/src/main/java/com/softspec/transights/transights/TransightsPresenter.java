package com.softspec.transights.transights;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by macbook on 6/4/2017 AD.
 */

public class TransightsPresenter implements Observer {

    private RemoteDataRepository remoteDataRepository;
    private Home home;

    public TransightsPresenter(RemoteDataRepository remoteDataRepository,Home home){
        this.remoteDataRepository = remoteDataRepository;
        this.home = home;
    }

    public void initialize(){
        remoteDataRepository.addObserver(this);
        remoteDataRepository.fetchAllStation();
    }

    public void search(String keyword){
        remoteDataRepository.search(keyword);
    }

    public void selectStation(String station){
        remoteDataRepository.selectStation(station);
    }

    public void showAll(){
        remoteDataRepository.showAll();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o != null) {
            String tmp = (String) o;
            if (tmp.equalsIgnoreCase("station"))
                remoteDataRepository.fetchAllPlace();
            else if (tmp.equalsIgnoreCase("place"))
                remoteDataRepository.fetchAllPriceAndTime();
        }
        else
            home.setPlaceList(remoteDataRepository.getPlaceList(), remoteDataRepository.getStationList());
    }
}
