package com.giedrius;

import com.giedrius.services.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class BeerAppApplication {

	@Autowired
	private SearchService searchService;

	private Double startLat,startLng;
	private Boolean exit = false;

	public static void main(String[] args) {
		SpringApplication.run(BeerAppApplication.class, args);
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(BeerAppApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
		BeerAppApplication app = context.getBean(BeerAppApplication.class);
		app.run();
	}

	private void run() {
		while(!exit)
			try {
				getUserInput();
				startSearch();
			} catch (Exception e) {
				log.error("Error occurred",e);
				System.out.println("### Error occurred ###");
				reset();
			}

	}

	private void startSearch() {
		long startTime = System.nanoTime();
		searchService.findLocalBreweries(startLat, startLng);
		long endTime = System.nanoTime();
		System.out.printf("Program took: %d ms \n", (endTime - startTime)/1000000 );
		promtExit();
	}

	private void reset() {
		startLat = null;
		startLng = null;
	}

	private boolean homeNull() {
		return startLat==null && startLng ==null;
	}


	private void getUserInput() {
		Scanner scan = new Scanner(System.in);
		while(homeNull()) {
			System.out.print("Enter latitude: ");
			try {
				startLat = Double.valueOf(scan.next());
				System.out.print("Enter longitude: ");
				startLng = Double.valueOf(scan.next());
			} catch (NumberFormatException e) {
				System.out.println("### Bad format ###");
			}
		}
	}
	private void promtExit() {
		String result = null;
		Scanner scan = new Scanner(System.in);
		System.out.print("Exit application?(Y/N) ");
		try {
			result = scan.next();
		} catch (NumberFormatException e) {
			System.out.println("### Bad format ###");
		}
		if(!result.equalsIgnoreCase("N"))
			exit = true;
		else
			reset();
	}
}