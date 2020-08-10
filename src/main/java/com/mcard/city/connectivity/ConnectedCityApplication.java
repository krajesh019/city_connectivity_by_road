package com.mcard.city.connectivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 

@SpringBootApplication
public class ConnectedCityApplication {

//	private static Logger appLogger = LoggerFactory.getLogger(ConnectedCityApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(ConnectedCityApplication.class, args);
	}

}
