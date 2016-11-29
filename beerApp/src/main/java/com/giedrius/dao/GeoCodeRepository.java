package com.giedrius.dao;

import com.giedrius.model.GeoCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface GeoCodeRepository extends Repository<GeoCode, Long> {
    @Query(value="SELECT *, (6371 * acos(cos(radians( :startLat )) * cos(radians(latitude)) * cos(radians(longitude) - radians( :startLong )) + sin(radians( :startLat)) * sin(radians(latitude )))) AS distance FROM GEO_CODE GROUP BY id HAVING distance < :limitDistance ORDER BY distance LIMIT :limitResults",nativeQuery=true)
    LinkedList<GeoCode> getGeoCodesWithDistanceList(@Param("startLat") Double startLat, @Param("startLong") Double startLong, @Param("limitDistance") int limitDistance, @Param("limitResults")  int limitResults);

    @Query(value="SELECT *, (6371 * acos(cos(radians( :startLat )) * cos(radians(latitude)) * cos(radians(longitude) - radians( :startLong )) + sin(radians( :startLat)) * sin(radians(latitude )))) AS distance FROM GEO_CODE GROUP BY id HAVING distance < :limitDistance ORDER BY distance LIMIT :limitResults",nativeQuery=true)
    GeoCode getGeoCodesWithDistance(@Param("startLat") Double startLat, @Param("startLong") Double startLong, @Param("limitDistance") int limitDistance, @Param("limitResults")  int limitResults);
}