package com.softspec.transights.transights;

import java.util.ArrayList;

/**
 * Created by macbook on 5/31/2017 AD.
 */

public class Station {
    private String stationName;
    private ArrayList<Place> placeList;
    private ArrayList<Station> arriveStation;
    private int price, time;

    public Station(String stationName, int price, int time){
        this.stationName = stationName;
        this.price = price;
        this.time = time;
        this.placeList = new ArrayList<>();
        this.arriveStation = new ArrayList<>();
    }

    public void addPlace(Place place){
        placeList.add(place);
    }

    public void addArriveStation(Station station){
        arriveStation.add(station);
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public ArrayList<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(ArrayList<Place> placeList) {
        this.placeList = placeList;
    }

    public ArrayList<Station> getArriveStation() {
        return arriveStation;
    }

    public void setArriveStation(ArrayList<Station> arriveStation) {
        this.arriveStation = arriveStation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
