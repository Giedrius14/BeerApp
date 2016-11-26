package com.giedrius.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by gsvazinskas on 11/25/2016.
 */
@Slf4j
@Component
public class AppSetupService implements DatabaseLoader {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createTables() {
        log.debug("Setting up data...");

        dropTable("BEER");
        dropTable("BREWERY");
        dropTable("CATEGORY");
        dropTable("GEO_CODE");
        dropTable("STYLE");

        jdbcTemplate.execute("CREATE TABLE BEER(ID SERIAL, BREWERY_ID INTEGER, NAME VARCHAR, CAT_ID INTEGER, STYLE_ID INTEGER, ABV FLOAT, IBU FLOAT, SRM FLOAT, UPC FLOAT, FILEPATH VARCHAR, DESCRIPTION VARCHAR, ADD_USER INTEGER, LAST_MODIFIED DATETIME) as select id,brewery_id,name,cat_id,style_id,abv,ibu,srm,upc,filepath,descript,add_user,last_mod from CSVREAD('classpath:csv/beers.csv')");
        jdbcTemplate.execute("CREATE TABLE BREWERY(ID SERIAL, NAME VARCHAR, ADDRESS1 VARCHAR, ADDRESS2 VARCHAR, CITY VARCHAR, STATE VARCHAR, CODE VARCHAR, COUNTRY VARCHAR, PHONE VARCHAR, WEBSITE VARCHAR, FILEPATH VARCHAR,DESCRIPTION VARCHAR, ADD_USER VARCHAR, LAST_MODIFIED DATETIME) as select id,name,address1,address2,city,state,code,country,phone,website,filepath,descript,add_user,last_mod from CSVREAD('classpath:csv/breweries.csv')");
        jdbcTemplate.execute("CREATE TABLE CATEGORY(ID SERIAL, CAT_NAME VARCHAR, LAST_MODIFIED DATETIME) as select id,cat_name,last_mod from CSVREAD('classpath:csv/categories.csv')");
        jdbcTemplate.execute("CREATE TABLE GEO_CODE(ID SERIAL, BREWERY_ID INTEGER, LATITUDE FLOAT, LONGITUDE FLOAT, ACCURACY VARCHAR(25)) as select id,brewery_id,latitude,longitude,accuracy from CSVREAD('classpath:csv/geocodes.csv')");
        jdbcTemplate.execute("CREATE TABLE STYLE(ID SERIAL, CAT_ID INTEGER, NAME VARCHAR, LAST_MODIFIED DATETIME) as select id,cat_id,style_name,last_mod from CSVREAD('classpath:csv/styles.csv')");
    }

    private void dropTable(String table) {
        log.debug("Droping table: "+table);
        jdbcTemplate.execute("DROP TABLE "+table+" IF EXISTS");
    }
}
