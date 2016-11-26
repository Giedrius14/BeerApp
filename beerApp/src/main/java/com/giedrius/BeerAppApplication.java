package com.giedrius;

import com.giedrius.dao.*;
import com.giedrius.services.DatabaseLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

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

		log.info("Beer Result :"+beerRepository.findOne(1L));
		log.info("Brewery Result :"+breweryRepository.findOne(1L));
		log.info("Category Result :"+categoryRepository.findOne(1L));
		log.info("Beer Result :"+styleRepository.findOne(1L));
		log.info("GeoCode Result :"+geoCodeRepository.findOne(1L));

		Double startLat = 51.742503;
		Double startLng = 19.432956;
/*		List<Map<String, Object>> result =jdbcTemplate.queryForList("SELECT *, SQRT(POWER(69.1 * (latitude - "+ startLat +"), 2) + POWER(69.1 * ("+startLng+" - longitude) * COS(latitude / 57.3), 2)) AS distance FROM GEO_CODE GROUP BY id HAVING distance < 1000  ORDER BY distance;");
		log.info("Distance result..:", result);*/

		List<Map<String, Object>> result2 =jdbcTemplate.queryForList("SELECT *, (6371 * acos(cos(radians("+startLat+")) * cos(radians(latitude)) * cos(radians(longitude) - radians("+startLng+")) + sin(radians("+startLat+")) * sin(radians(latitude )))) AS distance FROM GEO_CODE GROUP BY id HAVING distance < 300 ORDER BY distance LIMIT 20;");
		log.info("Distance result..:", result2);

		//TODO Google rodo 45.8
/*		"BREWERY_ID" -> "639"
		"LATITUDE" -> "51.84389877319336"
		"LONGITUDE" -> "10.753299713134766"
		"DISTANCE" -> "36.867335964083466"*/
//		SELECT latitude, longitude, SQRT(POW(69.1 * (latitude - [startlat]), 2) + POW(69.1 * ([startlng] - longitude) * COS(latitude / 57.3), 2)) AS distance FROM TableName HAVING distance < 25 ORDER BY distance;

/*		Double distance = distance(startLat,49.96220016,startLng,20.60029984);
		log.info("Distance result..:", distance);*/
	}

	public static double distance(double lat1, double lat2, double lon1,
								  double lon2) {

		final int R = 6371; // Radius of the earth 6371

		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lonDistance = Math.toRadians(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		distance = Math.pow(distance, 2);

		return Math.sqrt(distance);
	}
}
