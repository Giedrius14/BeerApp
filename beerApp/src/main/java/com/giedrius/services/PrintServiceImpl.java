package com.giedrius.services;

import com.giedrius.model.Beer;
import com.giedrius.model.GeoCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2016.11.26.
 */
@Slf4j
@Service
public class PrintServiceImpl implements PrintService {
    @Autowired
    private CalculationService calculationService;
    @Autowired
    private BeerService beerService;

    @Override
    public void printResult(List<GeoCode> result, GeoCode home) {
        if(result.size()>0) {
            List<Beer> beerList = new ArrayList<>();
            printBreweryList(result, beerList, home);
            printTotalDistance(result, home);
            printBeerTypes(beerList);
        }else {
            System.out.println("No results found");
        }
    }

    private void printBreweryList(List<GeoCode> result, List<Beer> beerList,GeoCode home) {
        System.out.printf("Found %d beer factories: \n",result.size());
        System.out.printf("\t [Home] ####################### Cords: %11.8f, %11.8f \n",home.getLatitude(),home.getLongitude());
        for(GeoCode brew:result) {
            System.out.printf("\t %11.8f, %11.8f \n", brew.getLatitude(), brew.getLongitude()); // cords for testing
//            System.out.printf("\t [%4d]  %-50s Cords: %11.8f, %11.8f \t distance: %3d km \n", brew.getBrewery().getId(), brew.getBrewery().getName(), brew.getLatitude(), brew.getLongitude(), brew.getDistance().longValue());
            beerList.addAll(beerService.getBeerByBrewery(brew));
        }
        System.out.printf("\t [Home] ####################### Cords: %11.8f, %11.8f \t distance: %3.0f km \n",home.getLatitude(),home.getLongitude(),calculationService.getDistanceToHome(result,home));
    }

    private void printTotalDistance(List<GeoCode> result,GeoCode home) {
        System.out.printf("\nTotal distance travelled: %.0f km \n", calculationService.getTotalDistanceSum(result, home));
    }

    private void printBeerTypes(List<Beer> beerList) {
        System.out.printf("Collected %d beer types: \n",beerList.size());
        for(Beer b:beerList)
            System.out.printf("\t\t %s \n",b.getName());
    }








}