package com.mcard.city.connectivity.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ConnectedCityServiceTest {

	@Autowired
	private ConnectedCityService connectedCityService;

	@Test
	public void isCityConnectedPostive() throws Exception {

		assertEquals(true, connectedCityService.isCityConnected("Boston", "Newark"));

	}

	@Test
	public void isCityConnectedNegative() throws Exception {

		assertNotEquals(true, connectedCityService.isCityConnected("Philadelphia", "Albany"));

	}

	@Test
	public void isCityConnectedInvalid() throws Exception {

		assertNotEquals(true, connectedCityService.isCityConnected("", null));

	}

}
