package com.softspec.transights.transights;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by macbook on 6/4/2017 AD.
 */

public class TransightsPresenter implements Observer {

    private RemoteDataRepository remoteDataRepository;
    private MainActivity mainActivity;

    public TransightsPresenter(RemoteDataRepository remoteDataRepository, MainActivity mainActivity){
        this.remoteDataRepository = remoteDataRepository;
        this.mainActivity = mainActivity;
    }

    public void initialize(){
        remoteDataRepository.addObserver(this);
        remoteDataRepository.fetchAllStation();
    }

    @Override
    public void update(Observable observable, Object o) {
        String tmp = (String)o;
        if(tmp.equalsIgnoreCase("station"))
            remoteDataRepository.fetchAllPlace();
        else if (tmp.equalsIgnoreCase("place"))
            remoteDataRepository.fetchAllPriceAndTime();
        else if (tmp.equalsIgnoreCase("finish"))
            mainActivity.setPlaceList(remoteDataRepository.getPlaceList(), remoteDataRepository.getStationList());
    }
}
