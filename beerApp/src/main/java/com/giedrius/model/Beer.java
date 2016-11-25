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
public class Beer {
    @Id
    private Long id;
    private Long breweryId;
    private String name;
    private Long catId;
    private Long styleId;
    private Double abv;
    private Double ibu;
    private Double srm;
    private Double upc;
    private String filepath;
    private String description;
    private Integer addUser;
    private Date lastModified;
}