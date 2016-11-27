package com.giedrius.services;

import com.giedrius.model.GeoCode;

import java.util.List;

/**
 * Created by User on 2016.11.27.
 */
public interface PrintService {
    void printResult(List<GeoCode> result, GeoCode home);
}
