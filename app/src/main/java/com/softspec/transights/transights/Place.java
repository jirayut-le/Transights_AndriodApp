package com.softspec.transights.transights;

/**
 * Created by macbook on 5/12/2017 AD.
 */

public class Place {
    private String placeID, placeName, description, imgSrc;

    public Place(){

    }
    public Place(String placeID, String placeName,String description, String imgSrc){
        this.placeID = placeID;
        this.placeName = placeName;
        this.description = description;
        this.imgSrc = imgSrc;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

}
