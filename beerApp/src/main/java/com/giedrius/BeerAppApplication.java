package com.giedrius;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BeerAppApplication {


    public static void main(String[] args) {
        SpringApplication.run(BeerAppApplication.class, args);
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(BeerAppApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
        Runner app = context.getBean(Runner.class);
        app.run();
    }
}