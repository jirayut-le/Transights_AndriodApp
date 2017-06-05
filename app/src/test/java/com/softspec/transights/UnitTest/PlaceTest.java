package com.softspec.transights.UnitTest;

import com.softspec.transights.transights.Place;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by macbook on 6/5/2017 AD.
 */

public class PlaceTest {

    private Place place;

    @Before
    public void createPlace(){
        place = new Place("B15-3", "Ekamai gateway", "Description Ekamai gateway", "img src of Ekamai gateway");
        assertTrue(place instanceof Place);
    }

    @Test
    public void getPlaceName(){
        assertEquals("Ekamai gateway", place.getPlaceName());
    }
}
