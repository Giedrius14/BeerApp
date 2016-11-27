package com.giedrius.services;

import com.giedrius.dao.GeoCodeRepository;
import com.giedrius.model.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by User on 2016.11.27.
 */
@Service
public class SearchServiceImpl {
    @Autowired
    private GeoCodeRepository geoCodeRepository;
    @Autowired
    private CalculationService calculationService;
    @Autowired
    private PrintService printService;

    private GeoCode home;
    private List<GeoCode> breweries;
}
