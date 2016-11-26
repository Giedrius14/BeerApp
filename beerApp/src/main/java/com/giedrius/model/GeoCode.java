package com.giedrius.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
@Data
@Entity
public class GeoCode {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "brewery_Id")
    private Brewery brewery;
    private Double latitude;
    private Double longitude;
    private String accuracy;
    private Double distance;
}