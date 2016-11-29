package com.giedrius.IT.services;

import com.giedrius.IT.BeerAppIT;
import com.giedrius.services.SearchService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.giedrius.fixtures.GeoCodeFixtures.GEOCODE;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class SearchServiceImplIT extends BeerAppIT {
    @Autowired
    private SearchService searchService;

    @Test
    public void findLocalBreweriesNearKaunas() throws Exception {
        searchService.findLocalBreweries(GEOCODE.getLatitude(), GEOCODE.getLongitude());
    }
    @Test
    public void findLocalBreweries1() throws Exception {
        searchService.findLocalBreweries(51.742503, 19.432956);
    }
    @Test
    public void findLocalBreweries2() throws Exception {
        searchService.findLocalBreweries(51.355468, 11.100790);
    }
}