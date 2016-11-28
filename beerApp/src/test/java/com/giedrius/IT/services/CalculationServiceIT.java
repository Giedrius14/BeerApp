package com.giedrius.IT.services;

import com.giedrius.IT.BeerAppIT;
import com.giedrius.services.CalculationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class CalculationServiceIT extends BeerAppIT {
    @Autowired
    private CalculationService calculationService;

    @Test
    public void distance() throws Exception {
        Assert.assertEquals(620.9,calculationService.distance(getHome().getLatitude(),getHome().getLongitude(),15d,15d),0.1);
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