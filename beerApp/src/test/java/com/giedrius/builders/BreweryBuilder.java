package com.giedrius.builders;

import com.giedrius.model.Brewery;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by gsvazinskas on 11/28/2016.
 */
public class BreweryBuilder {

    private Long id = 5l;
    private String name = "Abbaye de Leffe";
    private String address1 = "Dinant";
    private String address2 = "";
    private String city = "Dinant";
    private String state = "Namur";
    private String code = "";
    private String country = "Belgium";
    private String phone = "111111";
    private String website = "website";
    private String filepath = "filepath";
    private String description = "description";
    private Integer addUser = 0;
    private Date lastModified = null;

    public BreweryBuilder() {
    }
    public Brewery build(){
        return new Brewery(id, name, address1, address2, city, state, code, country, phone, website, filepath, description, addUser, lastModified);
    }

    public List<Brewery> buildList() {
        return Collections.singletonList(build());
    }
}