package com.giedrius.services;

import com.giedrius.model.GeoCode;

import java.util.List;

/**
 * Created by User on 2016.11.27.
 */
public interface CalculationService {
    double distance(double lat1, double lon1, double lat2, double lon2);

    double getDistanceSum(List<GeoCode> result);
    double getTotalDistanceSum(List<GeoCode> result,GeoCode home);
    double getDistanceToHome(List<GeoCode> result, GeoCode home);
}
