package com.giedrius.utils;

import com.giedrius.model.GeoCode;

import java.util.Comparator;

/**
 * Created by gsvazinskas on 11/29/2016.
 */
public class Comperators {

    public final static Comparator<GeoCode> COMPARE_DISTANCE = (p1, p2) -> Double.compare( p1.getDistance(), p2.getDistance());
}
