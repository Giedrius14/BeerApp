package com.giedrius.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
@Data
@Entity
public class Category {
    @Id
    private Long id;
    private String catName;
    private Date lastModified;
}