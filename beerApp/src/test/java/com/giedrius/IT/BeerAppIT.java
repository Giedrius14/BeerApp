package com.giedrius.IT;

import com.giedrius.model.GeoCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class BeerAppIT {

	protected List<GeoCode> getBreweries() {
		List<GeoCode> breweries = new ArrayList<>();
		breweries.add(new GeoCode(1d,1d,10d));
		breweries.add(new GeoCode(2d,2d,10d));
		breweries.add(new GeoCode(3d,3d,10d));
		return breweries;
	}
	protected GeoCode getHome() {
		return new GeoCode(11d,11d,10d);
	}
	@Test
	public void contextLoads() {
	}

}
