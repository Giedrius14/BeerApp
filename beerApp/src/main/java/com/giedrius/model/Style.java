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
public class Style {
    @Id
    private Long id;
    private Long catId;
    private String name;
    private Date lastModified;
}
