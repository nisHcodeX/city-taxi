package com.citytaxi.city_taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CityTaxiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityTaxiApplication.class, args);
	}

}
