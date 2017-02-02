package com.giedrius.services;

import com.giedrius.model.Beer;
import com.giedrius.model.GeoCode;

import java.util.List;

/**
 * Created by User on 2017.02.02.
 */
public interface BeerService {
    List<Beer> getBeerByBrewery(GeoCode d);
}
