package com.giedrius;

import com.giedrius.dao.*;
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

/*		log.info("Beer Result :"+beerRepository.findOne(1L));
		log.info("Brewery Result :"+breweryRepository.findOne(1L));
		log.info("Category Result :"+categoryRepository.findOne(1L));
		log.info("Beer Result :"+styleRepository.findOne(1L));*/
//		log.info("GeoCode Result :"+geoCodeRepository.findOne(1L)); // pakeista repository

		Double startLat = 51.742503;
		Double startLng = 19.432956;

		List<GeoCode> geoCodes = geoCodeRepository.getGeoCodesWithDistanceList(startLat,startLng,500,50);
		log.info("Distance result..:", geoCodes);

		GeoCode temp = geoCodes.get(0);
		Double distance= null;

		List<GeoCode> result = new ArrayList<>();
		result.add(temp);
			for (int i = 1; i < geoCodes.size(); i++) {
				distance = calculationService.distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
				if (temp.getDistance() > distance) {
					temp = geoCodes.get(i);
					temp.setDistance(distance);
					result.add(temp);
				}
			}
		log.info("After Loop result..:", result);
	}
}