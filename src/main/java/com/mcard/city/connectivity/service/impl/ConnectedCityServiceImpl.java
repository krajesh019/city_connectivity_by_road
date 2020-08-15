package com.mcard.city.connectivity.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mcard.city.connectivity.bean.City;
import com.mcard.city.connectivity.service.ConnectedCityService;

@Service
public class ConnectedCityServiceImpl implements ConnectedCityService {

	private static Logger appLogger = LoggerFactory.getLogger(ConnectedCityServiceImpl.class);

	@Value("${city.file.path}")
	private String cityFilePath;

	List<City> citiesList; 
	List<String>  connectedRoads;

	public boolean isCityConnected(String origin,String destination) {

		List<String>  connectedRoads = new ArrayList<>();
		connectedRoads.add(origin);

		connectedRoads = getConnectedCities(origin, destination, connectedRoads);

		if (connectedRoads.contains(destination)) {
			appLogger.info("Road Connectivity found between "+origin+" and "+destination);
			return true;
		}
		else {
			appLogger.info("Road Connectivity not found");
			return false;
		}
	}

	public List<String> getConnectedCities(String source, String destination,List<String> cityRoutes) {


		if(citiesList == null || citiesList.isEmpty()) {

			appLogger.info("citiesList is empty , reading the data from city.txt file....");
			citiesList = Collections.unmodifiableList(getCities());
		}

		List<String> childResult = citiesList.stream()
				.map(map -> map.getCityRoot(source))
				.filter(destObj -> (Objects.nonNull(destObj)) && !cityRoutes.contains(destObj)) 
				.collect(Collectors.toList());

		cityRoutes.addAll(childResult);

		if(!childResult.contains(destination))
			childResult.forEach(origin-> getConnectedCities(origin, destination, cityRoutes)); 

		if(childResult.isEmpty())
			cityRoutes.clear();

		return cityRoutes;
	}

	/**
	 * Read the data from city.txt file and stores each line in a file as City.java object
	 * @return list
	 */
	public List<City> getCities() {

		List<City> cityList = new ArrayList<>();

		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(cityFilePath).toURI());
			cityList =Files.readAllLines(path).stream().
					filter(s -> !s.trim().isEmpty())
					.map(line -> line.split(",|/n"))
					.map(a -> new City(a[0], a[1]))
					.collect(Collectors.toList());
		} catch (IOException | URISyntaxException e) {
			appLogger.error("Exception in ConnectedCityServiceImpl:getCities",e);
		} 

		return cityList;
	}

}
