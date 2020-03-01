package uk.ac.ed.bikerental;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Controller {
	private HashSet<Quote> quoteCollection = new HashSet<>();
	private HashSet<BikeProvider> bikeProviders = new HashSet<>();
	private HashMap<BikeType, Integer> bikesRequest = new HashMap<>();

	
	public HashSet<BikeProvider> getBikeProviders() {
		return bikeProviders;
	}

	public void setBikeProviders(HashSet<BikeProvider> bikeProviders) {
		this.bikeProviders = bikeProviders;
	}
	
	public boolean checkIfNotEmptyCollection() {
		return quoteCollection.size() > 0;
	}

	public HashSet<Quote> getQuoteCollection() throws Exception {
		if(checkIfNotEmptyCollection())
			return quoteCollection;
		else
			throw new Exception("No bikes available for the requested period. We are sorry for any inconvenience.");
	}
	
	public boolean isNearProvider(BikeProvider provider, Request request) {
		Location providerLocation = provider.getAddress();
		return providerLocation.isNearTo(request.getLocation());

	}
	
	public void setBikesRequest(Request request) {
		this.bikesRequest = request.getBikesRequested();
	}
	
	
	public boolean isValid(HashMap<BikeType, HashSet<Bike>> stock, BikeType type) {
		return (stock.containsKey(type) && stock.get(type).size() >= bikesRequest.get(type) );
	}
	
	
	public HashSet<Bike> allBikesAvailableType(HashMap<BikeType, HashSet<Bike>> stock, BikeType type, DateRange period, int number) {
		HashSet<Bike> bikesAvailableType = new HashSet<Bike>();
		for(Bike bike : stock.get(type)) {
			if(bike.isAvailable(period) && bikesAvailableType.size() < number)
				bikesAvailableType.add(bike);			
		}
		
		if(bikesAvailableType.size() == number)
			return bikesAvailableType;
		else 
			return null;
	}
	
	public void searchQuotes(Request customerRequest) {
		for(BikeProvider provider : this.bikeProviders) {
			setBikesRequest(customerRequest);
			boolean flag = true;                              // this provider has all the requested bikes in their stock
			DateRange period = customerRequest.getPeriod();
			HashMap<BikeType, HashSet<Bike>> bikesAvailableProvider = new HashMap<>();
			
			if( isNearProvider(provider, customerRequest) ) {
				for(BikeType typeBike : bikesRequest.keySet()) {
					HashMap<BikeType, HashSet<Bike>>providerStock = provider.getStock();
					int numberBikeType = bikesRequest.get(typeBike);
					
					if( isValid(providerStock, typeBike) ) {
						if( allBikesAvailableType(providerStock, typeBike, period, numberBikeType) != null ) {
							HashSet<Bike> bikesAvailableType = allBikesAvailableType(providerStock, typeBike, period, numberBikeType);
							bikesAvailableProvider.put(typeBike, bikesAvailableType);
						}
						else {
							flag = false;
							break;
						}
					}
					else {
						flag = false;
						break;
					}
				}
				if(flag == true) {
					Quote quote = new Quote(customerRequest, provider, bikesAvailableProvider);
					quoteCollection.add(quote);
				}
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Controller other = (Controller) obj;
		if (quoteCollection == null) {
			if (other.quoteCollection != null)
				return false;
		} else if (!quoteCollection.equals(other.quoteCollection))
			return false;
		return true;
	}

}
