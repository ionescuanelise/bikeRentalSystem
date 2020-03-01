package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class Delivery implements Deliverable{
	private Quote chosenQuote;
	private Location returnLocation;
	
	public Delivery(Booking booking) {
		this.chosenQuote = booking.getChosenQuote();
	}
	
	@Override
	public void onPickup() {
		HashMap<BikeType, HashSet<Bike>> bikes = chosenQuote.getBikes();
		for(BikeType type : bikes.keySet()) 
			for(Bike b : bikes.get(type)) 
				b.setStatus(StatusBike.IN_TRANSIT_FROM_PARTNER);
	}
	
	@Override
	public void onDropoff() {
		HashMap<BikeType, HashSet<Bike>> bikes = chosenQuote.getBikes();
		for(BikeType type : bikes.keySet()) 
			for(Bike b : bikes.get(type)) 
				b.setStatus(StatusBike.AVAILABLE);
	}
	
}
