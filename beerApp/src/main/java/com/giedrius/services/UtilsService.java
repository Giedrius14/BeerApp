package com.giedrius.services;

import com.giedrius.dao.BeerRepository;
import com.giedrius.model.Beer;
import com.giedrius.model.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016.11.26.
 */
@Service
public class UtilsService implements CalculationService, PrintService {
    @Autowired
    private CalculationService calculationService;
    @Autowired
    private BeerRepository beerRepository;

    public double distance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth 6371

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    @Override
    public void printResult(List<GeoCode> result, GeoCode home) {
        List<Beer> beerList = new ArrayList<>();
        printBreweryList(result, beerList,home);
        printTotalDistance(result, home);
        printBeerTypes(beerList);
    }

    private void printBreweryList(List<GeoCode> result, List<Beer> beerList,GeoCode home) {
        System.out.printf("Found %d beer factories: \n",result.size());
        System.out.printf("\t [Home] ####################### Cords: %11.8f, %11.8f \n",home.getLatitude(),home.getLongitude());
        for(GeoCode brew:result) {
//			System.out.printf("\t %11.8f, %11.8f \n", brew.getLatitude(), brew.getLongitude()); // cords for testing
            System.out.printf("\t [%4d]  %-50s Cords: %11.8f, %11.8f \t distance: %3d km \n", brew.getBrewery().getId(), brew.getBrewery().getName(), brew.getLatitude(), brew.getLongitude(), brew.getDistance().longValue());
            beerList.addAll(getBeerByBrewery(brew));
        }
        System.out.printf("\t [Home] ####################### Cords: %11.8f, %11.8f \t distance: %3f km \n",home.getLatitude(),home.getLongitude(),getDistanceToHome(result,home));
    }

    private void printTotalDistance(List<GeoCode> result,GeoCode home) {
        System.out.printf("\nTotal distance travelled: %.0f km \n", calculationService.getDistanceSum(result)+ getDistanceToHome(result, home));
    }

    private void printBeerTypes(List<Beer> beerList) {
        System.out.printf("Collected %d beer types: \n",beerList.size());
        for(Beer b:beerList)
            System.out.printf("\t\t %s \n",b.getName());
    }

    private double getDistanceToHome(List<GeoCode> result, GeoCode home) {
        return getDistance(result, home,result.size()-1);
    }

    private List<Beer> getBeerByBrewery(GeoCode d) {
        return beerRepository.findByBrewery(d.getBrewery().getId());
    }

    private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
        return calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
    }

    public double getDistanceSum(List<GeoCode> result) {
        double distanceSum = 0;
        for(GeoCode g:result)
            distanceSum+= g.getDistance();
        return distanceSum;
    }
}