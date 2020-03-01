package uk.ac.ed.bikerental;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class BikeProvider {
	
	private BigDecimal depositRate;
	private Collection<BikeProvider> partners;
	private HashMap<Integer, Booking> bookings = new HashMap<>();
	Location address;
	private HashMap<BikeType, HashSet<Bike>> stock = new HashMap<>();
	private HashMap<BikeType, BigDecimal> dailyRentalPricePerType = new HashMap<>();
	MathContext MATH_CTX = new MathContext(3,RoundingMode.HALF_UP);
	

	public BikeProvider(HashMap<BikeType, HashSet<Bike>> stock, BigDecimal depositRate, Collection<BikeProvider> partners, Location address) {
		this.stock = stock;
		this.depositRate = depositRate;
		this.partners = partners;
		this.address = address;
	}
	
	public void changeStatusOnReturn(int bookingNumber) {
		HashMap<BikeType, HashSet<Bike>> bikes = bookings.get(bookingNumber).getChosenQuote().getBikes();
		for(BikeType type : bikes.keySet()) 
			for(Bike b : bikes.get(type)) 
				b.setStatus(StatusBike.AVAILABLE);
	}
	
	public Location getAddress() {
		return address;
	}

	public void setAddress(Location address) {
		this.address = address;
	}

	public BigDecimal getDepositRate() {
		return depositRate;
	}

	public void setDepositRate(BigDecimal depositRate) {
		this.depositRate = depositRate;
	}

	public Collection<BikeProvider> getPartners() {
		return partners;
	}

	public void addPartner(BikeProvider partner) {
		this.partners.add(partner);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BikeProvider other = (BikeProvider) obj;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	public void addBikeToStock(Bike bike) {
		HashSet<Bike> bikesOfThisType = stock.containsKey(bike.getType()) ? this.stock.get(bike.getType()) : new HashSet<>();
		bikesOfThisType.add(bike);
		this.stock.put(bike.getType(), bikesOfThisType);
	}
	
	public HashMap<BikeType, BigDecimal> getDailyRentalPricePerType() {
		return dailyRentalPricePerType;
	}

	public HashMap<BikeType, HashSet<Bike>> getStock(){
		return stock;
	}
	
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
		BigDecimal roundedDailyPrice;
		roundedDailyPrice = dailyPrice.round(MATH_CTX);
		this.dailyRentalPricePerType.put(bikeType, roundedDailyPrice);
	}

	public HashMap<Integer, Booking> getBookings() {
		return bookings;
	}

	public void setBookings(HashMap<Integer, Booking> bookings) {
		this.bookings = bookings;
	}
    
	
}
