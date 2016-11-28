package com.giedrius;

import com.giedrius.services.SearchServiceImpl;
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
	private SearchServiceImpl searchService;

	private Double startLat,startLng;

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
//		getUserInput();

/*		 startLat = 51.742503; // 11 ir 18
		 startLng = 19.432956;*/
		startLat = 51.355468; // randa 12 fact ir 36 beer  211ms
		startLng = 11.100790;

		long startTime = System.nanoTime();
		searchService.findLocalBreweries(startLat, startLng);
		long endTime = System.nanoTime();
		System.out.printf("Program took: %d ms \n", (endTime - startTime)/1000000 );
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
				System.out.println("Bad format");
			}
		}
	}
}