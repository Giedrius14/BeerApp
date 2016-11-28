package com.giedrius.IT.services;

import com.giedrius.IT.BeerAppIT;
import com.giedrius.services.SearchService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class SearchServiceImplIT extends BeerAppIT {
    @Autowired
    private SearchService searchService;

    @Test
    public void findLocalBreweries() throws Exception {
        searchService.findLocalBreweries(getHome().getLatitude(),getHome().getLongitude());
    }

}