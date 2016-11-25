package com.giedrius.dao;

import com.giedrius.model.Beer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface BeerRepository extends CrudRepository<Beer, Long> {
    List<Beer> findAll();
}