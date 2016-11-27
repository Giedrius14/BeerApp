package com.giedrius;

import com.giedrius.dao.*;
import com.giedrius.model.Beer;
import com.giedrius.model.GeoCode;
import com.giedrius.services.CalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class BeerAppApplication {

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
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(BeerAppApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
		BeerAppApplication app = context.getBean(BeerAppApplication.class);
		app.run();
	}

	private void run() {
	/*	Scanner scan = new Scanner(System.in);
		System.out.println("Enter latitude: ");
		Double homeLat = Double.valueOf(scan.next());

		System.out.println("Enter longitude: ");
		Double homeLong = Double.valueOf(scan.next());*/

/*		Double startLat = homeLat;//51.742503
		Double startLng = homeLong;//19.432956*/
		Double startLat = 51.355468;
		Double startLng = 11.100790;
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
            if (distance > tempDist || tempDist<200) {
                temp = breweries.get(i);
                temp.setDistance(tempDist);
                result.add(temp);
				if(result.size()>3 && getDistanceSum(result)>1300)
					break;
			}
        }
	}

	private void printOut(List<GeoCode> result) {
		List<Beer> beerList = new ArrayList<>();

		System.out.printf("Found %d beer factories: \n",result.size());
		for(GeoCode brew:result) {
//			System.out.printf("\t %11.8f, %11.8f \n", brew.getLatitude(), brew.getLongitude()); // cords for testing
			System.out.printf("\t [%4d]  %-50s Cords: %11.8f, %11.8f \t distance: %3d km \n", brew.getBrewery().getId(), brew.getBrewery().getName(), brew.getLatitude(), brew.getLongitude(), brew.getDistance().longValue());
			beerList.addAll(getBeerByBrewery(brew));
		}
		System.out.printf("\nTotal distance travelled: %.0f km \n",getDistanceSum(result));
		System.out.printf("Collected %d beer types: \n",beerList.size());
		for(Beer b:beerList)
			System.out.printf("\t\t %s \n",b.getName());
	}

	private List<Beer> getBeerByBrewery(GeoCode d) {
		return beerRepository.findByBrewery(d.getBrewery().getId());
	}

	private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
		return calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
	}
}