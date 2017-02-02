package com.giedrius;

import com.giedrius.services.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

/**
 * Created by User on 2017.02.02.
 */
@Slf4j
public class Runner {

    @Autowired
    private SearchService searchService;

    private Double startLat, startLng;
    private Boolean exit = false;

    public void run() {
        while (!exit)
            try {
                getUserInput();
                startSearch();
            } catch (Exception e) {
                log.error("Error occurred", e);
                System.out.println("### Error occurred ###");
                reset();
            }
    }

    private void startSearch() {
        long startTime = System.nanoTime();
        searchService.findLocalBreweries(startLat, startLng);
        long endTime = System.nanoTime();
        System.out.printf("Program took: %d ms \n", (endTime - startTime) / 1000000);
        promptExit();
    }

    private void reset() {
        startLat = null;
        startLng = null;
    }

    private boolean homeNull() {
        return startLat == null && startLng == null;
    }


    private void getUserInput() {
        Scanner scan = new Scanner(System.in);
        while (homeNull()) {
            System.out.print("Enter latitude: ");
            try {
                startLat = Double.valueOf(scan.next());
                System.out.print("Enter longitude: ");
                startLng = Double.valueOf(scan.next());
            } catch (NumberFormatException e) {
                System.out.println("### Bad format ###");
            }
            scan.close();
        }
    }

    private void promptExit() {
        String result = null;
        Scanner scan = new Scanner(System.in);
        System.out.print("Exit application?(Y/N) ");
        try {
            result = scan.next();
        } catch (NumberFormatException e) {
            System.out.println("### Bad format ###");
        }
        scan.close();
        if (!result.equalsIgnoreCase("N"))
            exit = true;
        else
            reset();
    }
}
