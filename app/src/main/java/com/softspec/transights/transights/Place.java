package com.softspec.transights.transights;

/**
 * Created by macbook on 5/12/2017 AD.
 */

public class Place {
    private String placeID, placeName, stationName, imgSrc;
    private int avgRate;


    public Place(){

    }
    public Place(String placeID, String placeName, String stationName, String imgSrc){
        this.placeID = placeID;
        this.placeName = placeName;
        this.stationName = stationName;
        this.imgSrc = imgSrc;
        this.avgRate = avgRate;
    }

    @Override
    public String toString() {
        return placeName  + " @" + stationName;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(int avgRate) {
        this.avgRate = avgRate;
    }
}
