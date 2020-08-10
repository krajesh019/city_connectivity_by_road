package com.mcard.city.connectivity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcard.city.connectivity.service.ConnectedCityService;


@RestController
@RequestMapping("/")
public class ConnectedCityController {

	private static Logger appLogger = LoggerFactory.getLogger(ConnectedCityController.class);

	@Autowired
	private ConnectedCityService connectedCityService;

	@GetMapping("/connected")
	public ResponseEntity<String> getRoadConnectivity(@RequestParam(value = "origin", defaultValue = "") String origin,
			@RequestParam(value = "destination", defaultValue = "") String destination) {

		HttpHeaders responseHeaders = getRestHeader();

		try {
			appLogger.info("Start processing the request", origin, destination);
			return new ResponseEntity<>(connectedCityService.isCityConnected(origin.trim(), destination.trim()) ? "yes" : "no",
					responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Validation/Error:Connected City Errror ", responseHeaders,
					HttpStatus.BAD_REQUEST);
		}

	}

	private HttpHeaders getRestHeader() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(org.springframework.http.MediaType.TEXT_PLAIN);
		return responseHeaders;
	}

}
