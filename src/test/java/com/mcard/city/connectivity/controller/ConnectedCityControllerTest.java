package com.mcard.city.connectivity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mcard.city.connectivity.service.ConnectedCityService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ConnectedCityControllerTest {

	@InjectMocks
	private ConnectedCityController connectedCityController;
	@Mock
	private ConnectedCityService connectedCityService;

	@Test
	public void isCityConnectedPostive() throws Exception {
		when(connectedCityService.isCityConnected("Boston", "Newark")).thenReturn(true);
		ResponseEntity<String> responseEntity = connectedCityController.getRoadConnectivity("Boston", "Newark");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("yes", responseEntity.getBody());
	}

	@Test
	public void isCityConnectedNegative() throws Exception {
		when(connectedCityService.isCityConnected("Philadelphia", "Edison")).thenReturn(false);
		ResponseEntity<String> responseEntity = connectedCityController.getRoadConnectivity("Philadelphia", "Edison");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("no", responseEntity.getBody());
	}

}
