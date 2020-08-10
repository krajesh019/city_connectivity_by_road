package com.mcard.city.connectivity.bean;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class City {

	private String origin;
	private String destination; 

	public City(String from, String to) {
		this.origin = from;
		this.destination = to; 
	}

	public String getCityRoot(String citypath) {

		return citypath.equalsIgnoreCase(this.destination.trim()) ? origin.trim() : citypath.equalsIgnoreCase(this.origin.trim()) ? destination.trim() : null;
	}

}
