package com.giedrius.services;

import com.giedrius.dao.GeoCodeRepository;
import com.giedrius.model.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.giedrius.utils.Comperators.COMPARE_DISTANCE;

/**
 * Created by User on 2016.11.27.
 */
@Service
public class SearchServiceImpl implements SearchService{
    private static final int LIMIT_DISTANCE_FROM_HOME = 1000;
    private static final int LIMIT_RESULT_SIZE = 50;
    @Autowired
    private GeoCodeRepository geoCodeRepository;
    @Autowired
    private CalculationService calculationService;
    @Autowired
    private PrintService printService;

    private GeoCode home;


    public void findLocalBreweries(Double startLat, Double startLng) {
        setHome(startLat, startLng);
        if(homeNotNull()) {
            List<GeoCode> breweries = getClosestBreweriesFromHome();
            GeoCode temp = breweries.size()>0? breweries.get(0): null;
            breweries.remove(0);
            List<GeoCode> result = new ArrayList<>();
            if(temp !=null)
                result.add(temp);
            getResultList(breweries, temp, result);
            printService.printResult(result, home);
        }
    }

    private void setHome(Double startLat, Double startLng) {
        home = new GeoCode(startLat,startLng);
    }

    private void getResultList(List<GeoCode> breweries, GeoCode tmpBrewery, List<GeoCode> result) {
        GeoCode closest;
        for (int i = 1; i < breweries.size(); i++) {
            closest = getClosestBrewery(tmpBrewery,breweries);
            result.add(closest);
            breweries.remove(closest);
            if (ifResultDistanceIsOverTheLimit(breweries, result))
                break;
        }
    }

    private boolean ifResultDistanceIsOverTheLimit(List<GeoCode> breweries, List<GeoCode> result) {
        GeoCode closest;
        if(getTotalDistanceSum(result) >2000) {
            result.remove(result.size()-1);
            closest = getClosestBrewery(home,breweries);
            if(getTotalDistanceSum(result) + closest.getDistance()<=2000){
                result.add(closest);
            }
            return true;
        }
        return false;
    }

    private double getTotalDistanceSum(List<GeoCode> result) {
        return calculationService.getTotalDistanceSum(result, home);
    }

    private GeoCode getClosestBrewery( GeoCode startPoint, List<GeoCode> breweries) {
        GeoCode closest;
        breweries.forEach(brewery -> brewery.setDistance(getDistance(startPoint, brewery)));
        closest = breweries.stream().min(COMPARE_DISTANCE).get();
        return closest;
    }

    private LinkedList<GeoCode> getClosestBreweriesFromHome() {
        return geoCodeRepository.getGeoCodesWithDistanceList(home.getLatitude(),home.getLongitude(), LIMIT_DISTANCE_FROM_HOME, LIMIT_RESULT_SIZE);
    }

    private double getDistance(GeoCode temp, GeoCode brewery) {
        return calculationService.distance(temp.getLatitude(), temp.getLongitude(), brewery.getLatitude(), brewery.getLongitude());
    }
    private boolean homeNotNull() {
        return home.getLatitude()!=null && home.getLongitude() !=null;
    }
}