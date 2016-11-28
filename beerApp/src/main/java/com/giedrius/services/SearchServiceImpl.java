package com.giedrius.services;

import com.giedrius.dao.GeoCodeRepository;
import com.giedrius.model.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016.11.27.
 */
@Service
public class SearchServiceImpl {
    public static final int LIMIT_DISTANCE_FROM_HOME = 1500;
    public static final int LIMIT_RESULT_SIZE = 30;
    @Autowired
    private GeoCodeRepository geoCodeRepository;
    @Autowired
    private CalculationService calculationService;
    @Autowired
    private PrintService printService;

    private GeoCode home;
    private List<GeoCode> breweries;
    

    public void findLocalBreweries(Double startLat, Double startLng) {
        setHome(startLat, startLng);
        if(homeNotNull()) {
            breweries = getClosestBreweriesFromHome();
            GeoCode temp = breweries.get(0);
            List<GeoCode> result = new ArrayList<>();
            result.add(temp);
            getResultList(breweries, temp, result);
            printService.printResult(result, home);
        }
    }
    private void setHome(Double startLat, Double startLng) {
        home = new GeoCode(startLat,startLng);
    }

    private void getResultList(List<GeoCode> breweries, GeoCode tmpBrewery, List<GeoCode> result) { //TODO reikia improve
        Double currentDistance;
        for (int i = 1; i < breweries.size(); i++) {
            currentDistance = getDistance(breweries, tmpBrewery, i);
            if (checkDistance(tmpBrewery, currentDistance)) {
                tmpBrewery = breweries.get(i);
                tmpBrewery.setDistance(currentDistance);
                result.add(tmpBrewery);
                if(calculationService.getTotalDistanceSum(result, home)>1900)
                    break;
            }
        }
    }

    private boolean checkDistance(GeoCode tmpBrewery, Double currentDistance) {
        return tmpBrewery.getDistance() > currentDistance || currentDistance<300; //TODO reikia fix nes riboja rezultatus
    }

    private List<GeoCode> getClosestBreweriesFromHome() {
        return geoCodeRepository.getGeoCodesWithDistanceList(home.getLatitude(),home.getLongitude(), LIMIT_DISTANCE_FROM_HOME, LIMIT_RESULT_SIZE);
    }

    private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
        return calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
    }
    private boolean homeNotNull() {
        return home.getLatitude()!=null && home.getLongitude() !=null;
    }
}