package com.giedrius;

import com.giedrius.dao.*;
import com.giedrius.model.GeoCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class BeerAppApplication implements CommandLineRunner{

	@Autowired
	private BeerRepository beerRepository;
	@Autowired
	private BreweryRepository breweryRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private GeoCodeRepository geoCodeRepository;
	@Autowired
	private StyleRepository styleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BeerAppApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		log.info("Beer Result :"+beerRepository.findOne(1L));
		log.info("Brewery Result :"+breweryRepository.findOne(1L));
		log.info("Category Result :"+categoryRepository.findOne(1L));
		log.info("Beer Result :"+styleRepository.findOne(1L));
//		log.info("GeoCode Result :"+geoCodeRepository.findOne(1L)); // pakeista repository

		Double startLat = 51.742503;
		Double startLng = 19.432956;

		List<GeoCode> res = geoCodeRepository.getGeoCodesWithDistance(startLat,startLng,500,20);
		log.info("Distance result..:", res);
	}

}