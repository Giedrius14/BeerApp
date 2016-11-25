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
public class Brewery {
    @Id
    private Long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String code;
    private String country;
    private String phone;
    private String website;
    private String filepath;
    private String description;
    private Integer addUser;
    private Date lastModified;
}