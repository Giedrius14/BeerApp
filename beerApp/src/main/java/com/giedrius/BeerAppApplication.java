package com.giedrius;

import com.giedrius.dao.GeoCodeRepository;
import com.giedrius.model.GeoCode;
import com.giedrius.services.CalculationService;
import com.giedrius.services.PrintService;
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
	private PrintService printService;

	private Double startLat,startLng;
	private GeoCode home;
	private List<GeoCode> breweries;

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
		startLat = 51.355468;
		startLng = 11.100790;
		home = new GeoCode(startLat,startLng);

		breweries = getGeoCodesWithDistanceList();
		GeoCode temp = breweries.get(0);
		List<GeoCode> result = new ArrayList<>();
		result.add(temp);
		getResultList(breweries, temp, result);
		printService.printResult(result, home);
	}
	private void getResultList(List<GeoCode> breweries, GeoCode tmpBrewery, List<GeoCode> result) {
		Double currentDistance;
		for (int i = 1; i < breweries.size(); i++) {
			currentDistance = getDistance(breweries, tmpBrewery, i);
			if (addToResultList(breweries, tmpBrewery, result, currentDistance, i) == null)
				break;
		}
	}

	private GeoCode addToResultList(List<GeoCode> breweries, GeoCode tmpBrewery, List<GeoCode> result, Double currentDistance, int i) {
		if (tmpBrewery.getDistance() > currentDistance || currentDistance<200) {
            tmpBrewery = breweries.get(i);
            tmpBrewery.setDistance(currentDistance);
            result.add(tmpBrewery);
            if(result.size()>3 && calculationService.getDistanceSum(result)>1300)
				return null;
        }
		return tmpBrewery;
	}

	private List<GeoCode> getGeoCodesWithDistanceList() {
		return geoCodeRepository.getGeoCodesWithDistanceList(startLat,startLng,500,50);
	}

	private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
		return calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
	}
}