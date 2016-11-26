package com.giedrius.dao;

import com.giedrius.model.Beer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface BeerRepository extends CrudRepository<Beer, Long> {
    @Query(value = "SELECT * FROM BEER WHERE BREWERY_ID=:breweryId", nativeQuery = true)
    List<Beer> findByBrewery(@Param("breweryId")Long breweryId);
}