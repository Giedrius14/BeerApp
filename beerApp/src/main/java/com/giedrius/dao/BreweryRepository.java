package com.giedrius.dao;

import com.giedrius.model.Brewery;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface BreweryRepository extends CrudRepository<Brewery, Long> {
    List<Brewery> findAll();
}