package com.giedrius.services;

import com.giedrius.model.GeoCode;

import java.util.List;

/**
 * Created by User on 2017.02.02.
 */
public class CalculationServiceImpl implements CalculationService {

    @Override
    public double distance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth 6371

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    @Override
    public double getDistanceSum(List<GeoCode> result) {
        double distanceSum = 0;
        for (GeoCode g : result)
            distanceSum += g.getDistance();
        return distanceSum;
    }

    @Override
    public double getTotalDistanceSum(List<GeoCode> result, GeoCode home) {
        return getDistanceSum(result) + getDistanceToHome(result, home);
    }

    @Override
    public double getDistanceToHome(List<GeoCode> result, GeoCode home) {
        return getDistance(result, home, result.size() - 1);
    }

    private double getDistance(List<GeoCode> geoCodes, GeoCode temp, int i) {
        return distance(temp.getLatitude(), temp.getLongitude(), geoCodes.get(i).getLatitude(), geoCodes.get(i).getLongitude());
    }
}
