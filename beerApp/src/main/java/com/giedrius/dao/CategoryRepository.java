package com.giedrius.dao;

import com.giedrius.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();

}