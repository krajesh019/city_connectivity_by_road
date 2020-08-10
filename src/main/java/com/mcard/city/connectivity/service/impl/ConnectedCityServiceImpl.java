package com.mcard.city.connectivity.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

	public List<String> getConnectedCities(String source, String destination,List<String> citysRoutes) {

		List<String> childResult = getCities().stream().map(map -> map.getCityRoot(source))
				.filter(destObj -> (Objects.nonNull(destObj)) && !citysRoutes.contains(destObj)) 
				.collect(Collectors.toList());

		citysRoutes.addAll(childResult);

		if(!childResult.contains(destination))
			childResult.forEach(str-> getConnectedCities(str, destination, citysRoutes)); 

		return citysRoutes;
	}

	public List<City> getCities() {

		List<City> cityList = new ArrayList<>();

		try {
			cityList = new ArrayList<>();
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
