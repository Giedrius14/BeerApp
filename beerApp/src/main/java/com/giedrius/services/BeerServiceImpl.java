package com.giedrius.services;

import com.giedrius.dao.BeerRepository;
import com.giedrius.model.Beer;
import com.giedrius.model.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by User on 2017.02.02.
 */
public class BeerServiceImpl implements BeerService {
    @Autowired
    private BeerRepository beerRepository;

    public List<Beer> getBeerByBrewery(GeoCode d) {
        return beerRepository.findByBrewery(d.getBrewery().getId());
    }
}
