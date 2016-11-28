package com.giedrius.IT.services;

import com.giedrius.IT.BeerAppIT;
import com.giedrius.model.GeoCode;
import com.giedrius.services.CalculationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class CalculationServiceIT extends BeerAppIT {
    @Autowired
    private CalculationService calculationService;

    private List<GeoCode> getBreweries() {
        List<GeoCode> breweries = new ArrayList<>();
        breweries.add(new GeoCode(1d,1d,10d));
        breweries.add(new GeoCode(2d,2d,10d));
        breweries.add(new GeoCode(3d,3d,10d));
        return breweries;
    }
    private GeoCode getHome() {
        return new GeoCode(11d,11d,10d);
    }

    @Test
    public void distance() throws Exception {
//        calculationService.distance();
    }



    @Test
    public void getDistanceSum() throws Exception {
        Assert.assertEquals(30d,calculationService.getDistanceSum(getBreweries()),0);
    }

    @Test
    public void getTotalDistanceSum() throws Exception {
        Assert.assertEquals(40d,calculationService.getTotalDistanceSum(getBreweries(),getHome()),0);
    }

}