package com.giedrius;

import com.giedrius.dao.*;
import com.giedrius.services.DatabaseLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootApplication
public class BeerAppApplication implements CommandLineRunner{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DatabaseLoader databaseLoader;


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
		databaseLoader.createTables();

		log.info("Breer Result :"+beerRepository.findOne(1L));
		log.info("Brewery Result :"+breweryRepository.findOne(1L));
		log.info("Category Result :"+categoryRepository.findOne(1L));
		log.info("Beer Result :"+styleRepository.findOne(1L));
		log.info("GeoCode Result :"+geoCodeRepository.findOne(1L));
	}
}
