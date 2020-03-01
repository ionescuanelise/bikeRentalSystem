package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

enum StatusCollection{
	DELIVERY,
	PICKUP
}
enum StatusReturn{
	PARTNER,
	PROVIDER
}

public class Booking implements Deliverable{
	
	private int orderNumber;
	private StatusCollection modeOfCollection;
	private StatusReturn modeOfReturn;
	private Quote chosenQuote;
	private Customer customer;
	private BikeProvider provider;
	private BikeProvider returnPartner;
	private Location returnLocation;
	
	public Booking(StatusCollection modeOfCollection, StatusReturn modeOfReturn, Quote chosenQuote, Location returnLocation) {
		this.setOrderNumber(chosenQuote);
		this.modeOfCollection = modeOfCollection;
		this.setModeOfReturn(modeOfReturn);
		this.setLocationReturn(returnLocation);
		this.chosenQuote = chosenQuote;
		this.customer = chosenQuote.getCustomer();
		this.provider = chosenQuote.getProvider();
	}

	private void setLocationReturn(Location returnLocation) {
		this.returnLocation = returnLocation;
	}

	public StatusCollection getModeOfCollection() {
		return modeOfCollection;
	}

	public BikeProvider getProvider() {
		return provider;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Quote getChosenQuote() {
		return chosenQuote;
	}

	public BikeProvider getReturnPartner() {
		return returnPartner;
	}
	
	public void setReturnPartner(BikeProvider returnPartner) {
		this.returnPartner = returnPartner;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Quote q) {
		this.orderNumber = q.hashCode();
	}
	
	public boolean verifyPayement() {
		//to be implemented later
		return true;
	}
	
	public Invoice sendConfirmation() throws Exception {
		if(!verifyPayement())
			throw new Exception("Payment method failed");
		Invoice receipt = new Invoice(this.orderNumber, this.chosenQuote);
		return receipt;
	}
	
	public void addBikesUnavailability() {
		DateRange periodBooked = chosenQuote.getPeriod();
		for(BikeType type : chosenQuote.getBikes().keySet()) 
		    for (Bike bike : chosenQuote.getBikes().get(type)) {
			bike.addUnavailability(periodBooked);
		}
	}
	
	public void addBookingToBikeProvider() {
		int bookingNumber = this.getOrderNumber();
		this.provider.getBookings().put(bookingNumber, this); 
	}
	
	public void placeDeliveryOrder() {
			DeliveryServiceFactory.getDeliveryService().scheduleDelivery(this, this.getChosenQuote().getProvider().getAddress(), this.getChosenQuote().getCustomer().getCustomerLocation(), this.getChosenQuote().getPeriod().getStart());
	}
	
	@Override
	public void onPickup() {
		HashMap<BikeType, HashSet<Bike>> bikes = chosenQuote.getBikes();
		for(BikeType type : bikes.keySet()) 
			for(Bike b : bikes.get(type)) 
				b.setStatus(StatusBike.IN_TRANSIT_TO_CLIENT);
	}

	@Override
	public void onDropoff() {
		HashMap<BikeType, HashSet<Bike>> bikes = chosenQuote.getBikes();
		for(BikeType type : bikes.keySet()) 
			for(Bike b : bikes.get(type)) 
				b.setStatus(StatusBike.IN_CLIENT_S_POSSESION);
	}

	public StatusReturn getModeOfReturn() {
		return modeOfReturn;
	}

	public void setModeOfReturn(StatusReturn modeOfReturn) {
		this.modeOfReturn = modeOfReturn;
	}

}