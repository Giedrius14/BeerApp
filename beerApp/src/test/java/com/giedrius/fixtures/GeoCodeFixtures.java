package com.giedrius.fixtures;

import com.giedrius.builders.GeoCodeBuilder;
import com.giedrius.model.GeoCode;

import java.util.List;

/**
 * Created by User on 2016.11.28.
 */
public class GeoCodeFixtures {

    public static final GeoCode GEOCODE = new GeoCodeBuilder().build();
    public static final List<GeoCode> GEOCODE_LIST = new GeoCodeBuilder().withCoordinates(50d,50d).withDistance(20d).buildList();
}
