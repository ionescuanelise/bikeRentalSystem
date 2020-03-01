package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

public class Request {

	private Location location;
	private DateRange period;
	private HashMap<BikeType, Integer> bikesRequested = new HashMap<>(); 
	private Customer client;

	public Customer getClient() {
		return client;
	}

	public Request(Location location, DateRange period, HashMap<BikeType, Integer> bikesRequested, Customer client) {
		assert period!=null;
		this.location = location;
		this.period = period;
		this.bikesRequested = bikesRequested;
		this.client = client;
	}

	public Location getLocation() {
		return location;
	}

	public DateRange getPeriod() {
		return period;
	}

	public HashMap<BikeType, Integer> getBikesRequested() {
		return bikesRequested;
	}
}
