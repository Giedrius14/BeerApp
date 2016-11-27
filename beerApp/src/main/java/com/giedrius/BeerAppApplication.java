package com.giedrius;

import com.giedrius.dao.GeoCodeRepository;
import com.giedrius.model.GeoCode;
import com.giedrius.services.CalculationService;
import com.giedrius.services.PrintService;
import com.giedrius.services.SearchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class BeerAppApplication {

	@Autowired
	private GeoCodeRepository geoCodeRepository;
	@Autowired
	private CalculationService calculationService;
	@Autowired
	private PrintService printService;

	@Autowired
	private SearchServiceImpl searchService;

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
//		getUserInput();

		 startLat = 51.742503;//51.742503
		 startLng = 19.432956;//19.432956
/*		startLat = 51.355468;
		startLng = 11.100790;*/
		findLocalBreweries();
	}

	private void findLocalBreweries() {
		setHome();
		if(homeNotNull()) {
			breweries = getGeoCodesWithDistanceList();
			GeoCode temp = breweries.get(0);
			List<GeoCode> result = new ArrayList<>();
			result.add(temp);
			getResultList(breweries, temp, result);
			printService.printResult(result, home);
		}
	}

	private boolean homeNotNull() {
		return startLat!=null && startLng !=null;
	}

	private void setHome() {
		home = new GeoCode(startLat,startLng);
	}

	private void getUserInput() {
		Scanner scan = new Scanner(System.in);
		while(startLat==null && startLng ==null) {
			System.out.print("Enter latitude: ");
			try {
				startLat = Double.valueOf(scan.next());
				System.out.print("Enter longitude: ");
				startLng = Double.valueOf(scan.next());
			} catch (NumberFormatException e) {
				System.out.println("Bad format");
			}
		}
	}

	private void getResultList(List<GeoCode> breweries, GeoCode tmpBrewery, List<GeoCode> result) {
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
		return tmpBrewery.getDistance() > currentDistance || currentDistance<300;
	}

	private List<GeoCode> getGeoCodesWithDistanceList() {
		return geoCodeRepository.getGeoCodesWithDistanceList(startLat,startLng,500,100);
	}

	private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
		return calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
	}
}