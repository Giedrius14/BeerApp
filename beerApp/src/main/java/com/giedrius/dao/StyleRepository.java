package com.giedrius.dao;

import com.giedrius.model.Style;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface StyleRepository extends CrudRepository<Style, Long> {
    List<Style> findAll();

}