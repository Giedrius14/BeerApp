package com.giedrius.builders;

import com.giedrius.model.Brewery;
import com.giedrius.model.GeoCode;

import java.util.Collections;
import java.util.List;

import static com.giedrius.fixtures.BreweryFixtures.BREWERY;

/**
 * Created by User on 2016.11.28.
 */
public class GeoCodeBuilder {

    private Long id = 2000l;
    private Brewery brewery = null;
    private Double latitude = 54.9223112;
    private Double longitude = 23.9078721;
    private String accuracy = "APPROXIMATE";
    private Double distance = 10.0;

    public GeoCodeBuilder(){}

    public GeoCode build(){
        return new GeoCode(id, BREWERY, latitude, longitude, accuracy, distance);
    }

    public List<GeoCode> buildList() {
        return Collections.singletonList(new GeoCode(id, BREWERY, latitude, longitude, accuracy, distance));
    }

    public GeoCodeBuilder withDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public GeoCodeBuilder withCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }
}