package com.giedrius;

import com.giedrius.dao.*;
import com.giedrius.model.Beer;
import com.giedrius.model.GeoCode;
import com.giedrius.services.CalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class BeerAppApplication implements CommandLineRunner{

	@Autowired
	private GeoCodeRepository geoCodeRepository;
	@Autowired
	private CalculationService calculationService;
	@Autowired
	private BeerRepository beerRepository;
	@Autowired
	private BreweryRepository breweryRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StyleRepository styleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BeerAppApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		Double startLat = 51.742503;
		Double startLng = 19.432956;

		List<GeoCode> breweries = geoCodeRepository.getGeoCodesWithDistanceList(startLat,startLng,500,50);
		log.info("Distance result..:", breweries);

		GeoCode temp = breweries.get(0);
		Double distance= temp.getDistance();

		List<GeoCode> result = new ArrayList<>();
		result.add(breweries.get(0));
		getResultList(breweries, temp, distance, result);


		printOut(result);
	}

	private double getDistanceSum(List<GeoCode> result) {
		double distanceSum = 0;
		for(GeoCode g:result)
			distanceSum+= g.getDistance();
		return distanceSum;
	}

	private void getResultList(List<GeoCode> breweries, GeoCode temp, Double distance, List<GeoCode> result) {
		Double tempDist;
		for (int i = 1; i < breweries.size(); i++) {
            tempDist = getDistance(breweries, temp, i);
            if (distance > tempDist) {
                temp = breweries.get(i);
                temp.setDistance(tempDist);
                result.add(temp);
				if(getDistanceSum(result)>1300)
					break;
			}
        }
	}

	private void printOut(List<GeoCode> result) {
		List<Beer> beerList = new ArrayList<>();

		System.out.println("Found "+ result.size()+" beer factories:");
		for(GeoCode d:result) {
			System.out.println("[" + d.getBrewery().getId() + "] " + d.getBrewery().getName() + "  Cords:" + d.getLatitude().toString() + " , " + d.getLongitude().toString() + " distance: " + d.getDistance().longValue() + "km");
			beerList.addAll(beerRepository.findByBrewery(d.getBrewery().getId()));
		}
		System.out.println();
		System.out.println("Total distance travelled: "+ getDistanceSum(result)+"km");
		System.out.println();
		System.out.println();
		System.out.println("Collected "+ beerList.size() + "beer types:");
		for(Beer b:beerList)
			System.out.println(b.getName());
	}

	private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
		return calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
	}
}