package com.giedrius.fixtures;

import com.giedrius.builders.BreweryBuilder;
import com.giedrius.model.Brewery;
import java.util.List;

/**
 * Created by User on 2016.11.28.
 */
public class BreweryFixtures {

    public static final Brewery BREWERY = new BreweryBuilder().build();
    public static final List<Brewery> BREWERY_LIST = new BreweryBuilder().buildList();
}
