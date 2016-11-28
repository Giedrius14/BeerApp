package com.giedrius.IT.services;

import com.giedrius.IT.BeerAppIT;
import com.giedrius.services.PrintService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.giedrius.fixtures.GeoCodeFixtures.GEOCODE;
import static com.giedrius.fixtures.GeoCodeFixtures.GEOCODE_LIST;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class PrintServiceIT extends BeerAppIT {
    @Autowired
    private PrintService printService;

    @Test
    public void printResult() throws Exception {
        printService.printResult(GEOCODE_LIST, GEOCODE);
    }

}