package com.giedrius.dao;

import com.giedrius.model.GeoCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface GeoCodeRepository extends CrudRepository<GeoCode, Long> {
    List<GeoCode> findAll();

}