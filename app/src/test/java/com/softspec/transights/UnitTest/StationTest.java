package com.softspec.transights.UnitTest;

import com.softspec.transights.transights.Place;
import com.softspec.transights.transights.Station;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StationTest {

    private Station station;

    @Before
    public void setup(){
        station = new Station("Mochit", 0, 0);
    }

    @Test
    public void addPlace(){
        station.addPlace(new Place("ID001", "JJ Green", "Description of JJ Green" , "imgSrc of id-001"));
        assertEquals("JJ Green", station.getPlaceList().get(0).getPlaceName());
    }

    @Test
    public void addArriveStation(){
        station.addArriveStation(new Station("Siam", 42, 14));
        assertEquals("Siam", station.getArriveStation().get(0).getStationName());
    }

    @Test
    public void getPriceArriveStation() {
        String depart = "Mochit";
        String arrive = "Siam";
        if (station.getStationName().equalsIgnoreCase(depart))
            for (Station s : station.getArriveStation()) {
                if (s.getStationName().equalsIgnoreCase(arrive))
                    assertEquals(42, s.getPrice());
            }
    }

    @Test
    public void getTimeArriveStation(){
        String depart = "Mochit";
        String arrive = "Siam";
        if (station.getStationName().equalsIgnoreCase(depart))
            for (Station s : station.getArriveStation()) {
                if (s.getStationName().equalsIgnoreCase(arrive))
                    assertEquals(14, s.getTime());
            }
    }

}