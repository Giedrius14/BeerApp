package com.giedrius.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
@Data
@Entity
public class GeoCode {
    @Id
    private Long id;
    private Long breweryId;
    private Double latitude;
    private Double longitude;
    private String accuracy;
    private Double distance;
}