package com.giedrius.IT.services;

import com.giedrius.IT.BeerAppIT;
import com.giedrius.services.CalculationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.giedrius.fixtures.GeoCodeFixtures.GEOCODE;
import static com.giedrius.fixtures.GeoCodeFixtures.GEOCODE_LIST;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class CalculationServiceIT extends BeerAppIT {
    @Autowired
    private CalculationService calculationService;

    @Test
    public void distance() throws Exception {
        Assert.assertEquals(1901.1,calculationService.distance(GEOCODE.getLatitude(), GEOCODE.getLongitude(),53d,53d),0.1);
    }

    @Test
    public void getDistanceSum() throws Exception {
        Assert.assertEquals(20d,calculationService.getDistanceSum(GEOCODE_LIST),0);
    }

    @Test
    public void getTotalDistanceSum() throws Exception {
        Assert.assertEquals(1858.1,calculationService.getTotalDistanceSum(GEOCODE_LIST, GEOCODE),0.1);
    }
}